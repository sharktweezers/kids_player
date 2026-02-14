package com.dsokolov.kidsplayer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dsokolov.kidsplayer.resources.R
import com.dsokolov.kidsplayer.domain.model.PlayableItem
import com.dsokolov.kidsplayer.domain.model.PlayerPage
import com.dsokolov.kidsplayer.ui.theme.CONTROLS_HEIGHT
import com.dsokolov.kidsplayer.ui.theme.BORDER_GRID_2
import com.dsokolov.kidsplayer.ui.theme.ImageBorder
import com.dsokolov.kidsplayer.ui.theme.PAGE_INDICATOR_OFFSET
import com.dsokolov.kidsplayer.ui.theme.PAGE_INDICATOR_SIZE
import com.dsokolov.kidsplayer.ui.theme.PLAYABLE_ITEM_BORDER
import kotlinx.coroutines.launch

@Composable
internal fun PageContent(
    pagesCount: Int,
    currentPage: Int,
    columnsCount: Int,
    pages: List<PlayerPage>,
    onPageChange: (Int) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        initialPage = currentPage,
        pageCount = { pagesCount }
    )

    LaunchedEffect(pagerState.currentPage) {
        onPageChange(pagerState.currentPage)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                bottom = CONTROLS_HEIGHT.dp,
                start = BORDER_GRID_2.dp,
                end = BORDER_GRID_2.dp,
            ),
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { pageNumber: Int ->
            Page(
                page = pages[pageNumber],
                columnsCount = columnsCount,
            )
        }

        Row(
            Modifier
                .height(PAGE_INDICATOR_SIZE.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .offset(y = PAGE_INDICATOR_OFFSET.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(pagerState.pageCount) { pageNumber ->
                Image(
                    painter = painterResource(
                        id = if (pageNumber == currentPage) {
                            R.drawable.player_page_active
                        } else {
                            R.drawable.player_page_inactive
                        }
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(PAGE_INDICATOR_SIZE.dp)
                        .clickable(onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pageNumber)
                            }
                        })
                )
            }
        }
    }
}

@Composable
private fun Page(
    page: PlayerPage,
    columnsCount: Int,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(columnsCount),
            modifier = Modifier.wrapContentSize(),
            verticalArrangement = Arrangement.spacedBy(BORDER_GRID_2.dp),
            horizontalArrangement = Arrangement.spacedBy(BORDER_GRID_2.dp),
        ) {
            items(
                items = page.items,
                key = { item -> item.id }
            ) { item ->
                Image(
                    painter = painterResource(id = item.iconId),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(CircleShape)
                        .border(PLAYABLE_ITEM_BORDER.dp, ImageBorder, CircleShape)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewPageContent() {
    val page = PlayerPage(
        playableItems = listOf(
            PlayableItem(
                id = 0,
                markAsPlayed = false,
                iconId = R.drawable.s0,
                audioId = R.raw.aisberg_v_okeane,
            ),
            PlayableItem(
                id = 1,
                markAsPlayed = false,
                iconId = R.drawable.s1,
                audioId = R.raw.belye_snezhinki,
            ),
            PlayableItem(
                id = 2,
                markAsPlayed = false,
                iconId = R.drawable.s2,
                audioId = R.raw.buratino,
            ),
            PlayableItem(
                id = 3,
                markAsPlayed = false,
                iconId = R.drawable.s3,
                audioId = R.raw.chelovek_sobake_drug,
            ),
            PlayableItem(
                id = 4,
                markAsPlayed = false,
                iconId = R.drawable.s4,
                audioId = R.raw.daleko_daleko,
            ),
            PlayableItem(
                id = 5,
                markAsPlayed = false,
                iconId = R.drawable.s5,
                audioId = R.raw.ded_moroz_pogodi,
            ),
            PlayableItem(
                id = 6,
                markAsPlayed = false,
                iconId = R.drawable.s6,
                audioId = R.raw.duet_glupogo_korolya_i_prekrasnoy_princesi,
            ),
            PlayableItem(
                id = 7,
                markAsPlayed = false,
                iconId = R.drawable.s7,
                audioId = R.raw.esli_dobryy_ty,
            ),
            PlayableItem(
                id = 8,
                markAsPlayed = false,
                iconId = R.drawable.s8,
                audioId = R.raw.gimn_neznayki_i_ego_druzey,
            ),
            PlayableItem(
                id = 9,
                markAsPlayed = false,
                iconId = R.drawable.s9,
                audioId = R.raw.goluboi_vagon,
            ),
            PlayableItem(
                id = 10,
                markAsPlayed = false,
                iconId = R.drawable.s10,
                audioId = R.raw.kaby_ne_bylo_zimy,
            ),
            PlayableItem(
                id = 11,
                markAsPlayed = false,
                iconId = R.drawable.s11,
                audioId = R.raw.karusel,
            ),
        ),
        number = 0,
    )
    PageContent(
        pagesCount = 1,
        currentPage = 0,
        columnsCount = 3,
        pages = listOf(page),
        onPageChange = {},
    )
}