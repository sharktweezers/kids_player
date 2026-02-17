package com.dsokolov.kidsplayer.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dsokolov.kidsplayer.di.Di
import com.dsokolov.kidsplayer.presentation.PlayerViewModel
import com.dsokolov.kidsplayer.presentation.UiPlayerState
import com.dsokolov.kidsplayer.ui.theme.KidsPlayerTheme
import com.dsokolov.kidsplayer.utils.viewmodel.assistedViewModel

@Composable
internal fun PlayerScene() {
    val configuration = LocalConfiguration.current
    val vm: PlayerViewModel = assistedViewModel {
        Di.getComponent().getPlayerViewModelFactory().create(
            configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        )
    }
    // val vm: PlayerViewModel = viewModel(factory = ViewModelFactoryHolder.factory()) - без Assisted параметров в конструкторе

    KidsPlayerTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            val screenState by vm.state.collectAsStateWithLifecycle()

            when (val state = screenState) {
                is UiPlayerState.UiPlayerLoading -> Unit
                is UiPlayerState.UiPlayerFill -> {
                    FilledPlayerScreen(vm, state, innerPadding)
                }
            }
        }
    }
}