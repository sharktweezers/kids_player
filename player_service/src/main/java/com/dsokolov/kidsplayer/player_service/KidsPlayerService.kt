package com.dsokolov.kidsplayer.player_service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.os.IBinder
import android.view.View.VISIBLE
import android.widget.RemoteViews
import androidx.core.app.NotificationManagerCompat
import com.dsokolov.kidsplayer.domain.interactor.PlayerInteractor
import com.dsokolov.kidsplayer.domain.model.PlayerEvent
import com.dsokolov.kidsplayer.player_service.di.PlayerServiceComponentHolder
import com.dsokolov.kidsplayer.resources.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import javax.inject.Inject

class KidsPlayerService : Service() {

    @Inject
    lateinit var playerInteractor: PlayerInteractor

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val notificationManager: NotificationManagerCompat by lazy {
        NotificationManagerCompat.from(
            baseContext.applicationContext
        )
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.action?.let { action ->
            when (action) {
                getString(R.string.repeat_all) -> {

                }

                getString(R.string.repeat) -> {

                }

                getString(R.string.play) -> {

                }

                getString(R.string.next) -> {

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
        createNotification()
    }

    override fun onDestroy() {
        playerInteractor.onPlayerEventChanged(PlayerEvent.Stop)
        coroutineScope.coroutineContext.cancelChildren()
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
            R.id.repeat_all,
            getPendingSelfIntent(getString(R.string.repeat_all))
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

    internal companion object {

        const val NOTIFICATION_ID = 101
        private const val CHANNEL_ID = "channel_id"
        private const val CHANNEL_NAME = "channel_name"
    }
}