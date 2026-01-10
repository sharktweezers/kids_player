package com.dsokolov.kidsplayer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dsokolov.kidsplayer.R
import com.dsokolov.kidsplayer.domain.PlayerPage
import com.dsokolov.kidsplayer.presentation.PlayableItem
import com.dsokolov.kidsplayer.ui.theme.CONTROLS_PADDING

@Composable
fun PageContent(page: PlayerPage) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = CONTROLS_PADDING.dp),
        contentAlignment = Alignment.Center
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(page.columnsCount),
            modifier = Modifier.wrapContentSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(page.items) { item ->
                Image(
                    painter = painterResource(id = item.iconId),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(CircleShape)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewPageContent() {
    val page = PlayerPage(
        columnsCount = 3,
        items = listOf(
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
        )
    )
    PageContent(page)
}