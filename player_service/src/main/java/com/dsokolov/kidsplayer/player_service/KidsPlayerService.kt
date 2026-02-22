package com.dsokolov.kidsplayer.player_service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.Service
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Color
import android.net.Uri.*
import android.os.IBinder
import android.view.View.VISIBLE
import android.widget.RemoteViews
import androidx.core.app.NotificationManagerCompat
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.dsokolov.kidsplayer.domain.interactor.PlayerInteractor
import com.dsokolov.kidsplayer.domain.model.PlayableItem
import com.dsokolov.kidsplayer.domain.model.PlayerEvent
import com.dsokolov.kidsplayer.domain.model.PlayerSideEffect
import com.dsokolov.kidsplayer.injector.test.DispatchersProvider
import com.dsokolov.kidsplayer.player_service.di.PlayerServiceComponentHolder
import com.dsokolov.kidsplayer.resources.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class KidsPlayerService : Service() {

    @Inject
    lateinit var playerInteractor: PlayerInteractor

    private val coroutineScope: CoroutineScope by lazy {
        CoroutineScope(SupervisorJob() + DispatchersProvider.io())
    }

    val notificationManager: NotificationManagerCompat by lazy {
        NotificationManagerCompat.from(
            baseContext.applicationContext
        )
    }

    private val player: ExoPlayer by lazy {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(C.USAGE_MEDIA)
            .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
            .build()

        ExoPlayer
            .Builder(baseContext)
            .setAudioAttributes(audioAttributes, /* handleAudioFocus= */ true)
            .build()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.action?.let { action ->
            when (action) {
                getString(R.string.open_ui) -> {
                    val pm = baseContext.packageManager
                    pm.getLaunchIntentForPackage("com.dsokolov.kidsplayer")?.let { intent ->
                        baseContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                    }
                }

                getString(R.string.repeat) -> {
                    coroutineScope.launch(DispatchersProvider.immediate()) {
                        playerInteractor.onPlayerEvent(PlayerEvent.RepeatClicked)
                    }
                }

                getString(R.string.play) -> {
                    coroutineScope.launch(DispatchersProvider.immediate()) {
                        playerInteractor.onPlayerEvent(PlayerEvent.PlayPauseBtnClicked)
                    }
                }

                getString(R.string.next) -> {
                    coroutineScope.launch(DispatchersProvider.immediate()) {
                        playerInteractor.onPlayerEvent(PlayerEvent.NextClicked)
                    }
                }

                getString(R.string.close) -> {
                    stopService()
                }
            }
        }
        return START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        PlayerServiceComponentHolder.getComponent().inject(this)
        coroutineScope.launch(DispatchersProvider.immediate()) {
            playerInteractor.onPlayerEvent(PlayerEvent.OnCreateService)
        }
        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                if (playbackState == Player.STATE_ENDED) {
                    // The entire playlist has finished.
                    coroutineScope.launch(DispatchersProvider.immediate()) {
                        playerInteractor.onPlayerEvent(PlayerEvent.NextClicked)
                    }
                }
            }
        })
        createNotification()
        subscribeOnPlayerSideEffects()
    }

    override fun onDestroy() {
        coroutineScope.launch(DispatchersProvider.immediate()) {
            playerInteractor.onPlayerEvent(PlayerEvent.OnDestroyService)
        }
        coroutineScope.coroutineContext.cancelChildren()
        player.stop()
        player.release()
        super.onDestroy()
    }

    private fun createNotification() {
        val contentView = RemoteViews(packageName, R.layout.controls)
        createNotificationChannel()
        val notificationBuilder =
            androidx.core.app.NotificationCompat.Builder(this@KidsPlayerService, CHANNEL_ID)
                .apply {
                    setChannelId(CHANNEL_ID)
                    setVisibility(androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC)
                    setSmallIcon(R.mipmap.ic_launcher)
                }

        contentView.setOnClickPendingIntent(
            R.id.open_ui,
            getPendingSelfIntent(getString(R.string.open_ui))
        )

        contentView.setOnClickPendingIntent(
            R.id.play,
            getPendingSelfIntent(getString(R.string.play))
        )

        contentView.setOnClickPendingIntent(
            R.id.next,
            getPendingSelfIntent(getString(R.string.next))
        )
        contentView.setOnClickPendingIntent(
            R.id.repeat,
            getPendingSelfIntent(getString(R.string.repeat))
        )
        contentView.setOnClickPendingIntent(
            R.id.close,
            getPendingSelfIntent(getString(R.string.close))
        )
        contentView.setViewVisibility(R.id.close, VISIBLE)

        notificationBuilder.setContent(contentView)

        startForeground(NOTIFICATION_ID, notificationBuilder.build())

        coroutineScope.launch {
            playerInteractor.getPlayerDataFlow.collect { data ->
                if (data.isPlay) {
                    contentView.setImageViewResource(R.id.play, R.drawable.pause)
                } else {
                    contentView.setImageViewResource(R.id.play, R.drawable.play)
                }
                startForeground(NOTIFICATION_ID, notificationBuilder.build())
            }
        }
    }

    private fun createNotificationChannel() {
        val chan = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME, NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        notificationManager.createNotificationChannel(chan)
    }

    private fun getPendingSelfIntent(action: String): PendingIntent {
        val intent = Intent(this, javaClass)
        intent.action = action
        return PendingIntent.getService(this, 0, intent, FLAG_UPDATE_CURRENT)
    }

    private fun stopService() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun subscribeOnPlayerSideEffects() {
        coroutineScope.launch {
            playerInteractor
                .serviceSideEffectFlow
                .onEach { sideEffect ->
                    when (sideEffect) {
                        is PlayerSideEffect.PlayerServiceSideEffect.Stop -> { player.stop() }

                        is PlayerSideEffect.PlayerServiceSideEffect.Repeat -> {
                            val mediaItem = sideEffect.playableItem.getMediaItem()
                            player.setMediaItem(mediaItem)
                            player.prepare()
                            player.play()
                        }

                        is PlayerSideEffect.PlayerServiceSideEffect.PlayMediaId -> {
                            val mediaItem = sideEffect.playableItem.getMediaItem()

                            if (mediaItem == player.currentMediaItem) {
                                player.prepare()
                                player.play()
                            } else {
                                player.setMediaItem(mediaItem)
                                player.prepare()
                                player.play()
                            }
                        }
                    }
                }
                .flowOn(DispatchersProvider.main())
                .stateIn(coroutineScope)
        }
    }

    private fun PlayableItem.getMediaItem(): MediaItem {
        val audioId = this.audioId
        val audioUri =
            Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .path(audioId.toString())
                .build()
        return MediaItem.fromUri(audioUri)
    }

    internal companion object {

        const val NOTIFICATION_ID = 101

        private const val CHANNEL_ID = "channel_id"

        private const val CHANNEL_NAME = "channel_name"
    }
}