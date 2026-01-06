package com.dsokolov.kidsplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.dsokolov.kidsplayer.presentation.PlayerViewModel
import com.dsokolov.kidsplayer.ui.PlayerScene

class MainActivity : ComponentActivity() {

    val model: PlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlayerScene()
        }
    }
}