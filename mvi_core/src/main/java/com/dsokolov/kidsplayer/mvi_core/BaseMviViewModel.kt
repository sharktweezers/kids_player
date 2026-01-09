package com.dsokolov.kidsplayer.mvi_core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseMviViewModel : ViewModel() {
    protected fun launchUnit(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(block = block)
    }
}