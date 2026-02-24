package com.dsokolov.kidsplayer.ui

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

const val HORIZONTAL_PAGER_TAG = "horizontal_pager"

const val PAGE_NUMBER_TAG = "page_number"

const val PAGE_INDICATOR_TAG = "page_indicator"

const val PLAYING_ITEM_TAG = "played_item"

const val ITEM_ID_TAG = "item_id"

@Composable
internal fun PageContent(
    playingItemId: Int?,
    currentPage: Int,
    columnsCount: Int,
    pages: List<PlayerPage>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    onItemClick: (Int) -> Unit,
) {
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
            modifier = Modifier
                .fillMaxSize()
                .testTag(HORIZONTAL_PAGER_TAG),
        ) { pageNumber: Int ->
            Page(
                playingItemId = playingItemId,
                page = pages[pageNumber],
                columnsCount = columnsCount,
                onItemClick = onItemClick,
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
                val indicatorDrawableId = if (pageNumber == currentPage) {
                    R.drawable.player_page_active
                } else {
                    R.drawable.player_page_inactive
                }

                Image(
                    painter = painterResource(id = indicatorDrawableId),
                    contentDescription = indicatorDrawableId.toString(),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(PAGE_INDICATOR_SIZE.dp)
                        .clickable(onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pageNumber)
                            }
                        })
                        .testTag(PAGE_INDICATOR_TAG + pageNumber)
                )
            }
        }
    }
}

@Composable
private fun Page(
    playingItemId: Int?,
    page: PlayerPage,
    columnsCount: Int,
    onItemClick: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag(PAGE_NUMBER_TAG + page.number),
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
                if (item.id == playingItemId) {
                    val infiniteTransition = rememberInfiniteTransition()

                    val scale by infiniteTransition.animateFloat(
                        initialValue = 1f,
                        targetValue = 0.75f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(1250),
                            repeatMode = RepeatMode.Reverse,
                        )
                    )

                    Image(
                        painter = painterResource(id = item.iconId),
                        contentDescription = PLAYING_ITEM_TAG,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .scale(scale)
                            .aspectRatio(1f)
                            .clip(CircleShape)
                            .clickable(onClick = { onItemClick.invoke(item.id) })
                            .border(PLAYABLE_ITEM_BORDER.dp, ImageBorder, CircleShape)
                            .testTag(ITEM_ID_TAG + item.id),
                    )
                } else {
                    Image(
                        painter = painterResource(id = item.iconId),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(CircleShape)
                            .clickable(onClick = { onItemClick.invoke(item.id) })
                            .border(PLAYABLE_ITEM_BORDER.dp, ImageBorder, CircleShape)
                            .testTag(ITEM_ID_TAG + item.id),
                    )
                }
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
        playingItemId = null,
        currentPage = 0,
        columnsCount = 3,
        pages = listOf(page),
        pagerState = rememberPagerState(
            initialPage = 0,
            pageCount = { 1 }
        ),
        coroutineScope = rememberCoroutineScope(),
        onItemClick = { 0 }
    )
}