package com.dsokolov.kidsplayer.ui

import android.content.Intent
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.dsokolov.kidsplayer.player_service.KidsPlayerService
import com.dsokolov.kidsplayer.presentation.PlayerUiSideEffect
import com.dsokolov.kidsplayer.presentation.PlayerViewModel
import com.dsokolov.kidsplayer.presentation.UiPlayerState
import com.dsokolov.kidsplayer.resources.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun PlayerScreenLaunchedEffect(
    vm: PlayerViewModel,
    pagerState: PagerState,
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        vm.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is PlayerUiSideEffect.StartPlayerService -> {
                    context.startService(
                        Intent(context, KidsPlayerService::class.java)
                    )
                }

                is PlayerUiSideEffect.ToPage -> {
                    pagerState.animateScrollToPage(sideEffect.pageNumber)
                }
            }
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        vm.onPageChanged(pagerState.currentPage)
    }
}

@Composable
internal fun PlayerScreenDisposableEffect(
    vm: PlayerViewModel,
    coroutineScope: CoroutineScope,
) {
    val context = LocalContext.current
    val action = stringResource(R.string.close)
    DisposableEffect(Unit) {
        var isPlay = false
        val job = coroutineScope.launch {
            vm.state.collect {
                isPlay = (it as? UiPlayerState.UiPlayerFill)?.isPlay ?: false
            }
        }

        onDispose {
            job.cancel()
            if (!isPlay) {
                val intent = Intent(context, KidsPlayerService::class.java)
                intent.action = action
                context.startService(intent)
            }
        }
    }
}