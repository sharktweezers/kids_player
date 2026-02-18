package com.dsokolov.kidsplayer.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import com.dsokolov.kidsplayer.presentation.PlayerViewModel
import com.dsokolov.kidsplayer.presentation.UiPlayerState
import com.dsokolov.kidsplayer.resources.R

@Composable
internal fun FilledPlayerScreen(
    vm: PlayerViewModel,
    state: UiPlayerState.UiPlayerFill,
    innerPadding: PaddingValues,
) {
    val coroutineScope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current

    vm.onConfigurationChanged(
        isVerticalScreenOrientation = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    )

    val pagerState = rememberPagerState(
        initialPage = state.currentPage,
        pageCount = { state.pagesCount }
    )

    PlayerScreenLaunchedEffect(vm, pagerState)
    PlayerScreenDisposableEffect(vm, coroutineScope)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
    ) {
        Image(
            painter = painterResource(id = R.drawable.gradient),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        PageContent(
            playingItemId = if (state.isPlay) state.currentItemId else null,
            currentPage = state.currentPage,
            columnsCount = state.columnsCount,
            pages = state.pages,
            pagerState = pagerState,
            coroutineScope = coroutineScope,
            onItemClick = vm::onItemClick
        )
        BottomPanel(
            isPlay = state.isPlay,
            playerViewModel = vm,
        )
    }

}