package com.dsokolov.kidsplayer.utils

import kotlinx.coroutines.CoroutineDispatcher

class DispatchersProvider(
    val io: CoroutineDispatcher,
    val default: CoroutineDispatcher,
    val main: CoroutineDispatcher,
    val immediate: CoroutineDispatcher,
)