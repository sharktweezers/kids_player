package com.dsokolov.kidsplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dsokolov.kidsplayer.di.Di
import com.dsokolov.kidsplayer.presentation.PlayerViewModel
import com.dsokolov.kidsplayer.ui.PlayerScene
import com.dsokolov.kidsplayer.utils.viewmodel.viewModelsWithRuntimeArgs
import javax.inject.Inject

class PlayerActivity : ComponentActivity() {
    @Inject
    internal lateinit var viewModelFactory: PlayerViewModel.Factory

    private val vm: PlayerViewModel by viewModelsWithRuntimeArgs {
        viewModelFactory.create(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Di.getComponent().inject(this)

        enableEdgeToEdge()
        setContent {
            PlayerScene()
        }
    }
}