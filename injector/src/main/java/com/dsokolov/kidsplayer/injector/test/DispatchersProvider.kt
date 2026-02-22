package com.dsokolov.kidsplayer.injector.test

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object DispatchersProvider {
    @Volatile
    private var holder = DispatchersHolder(
        io = Dispatchers.IO,
        default = Dispatchers.Default,
        main = Dispatchers.Main,
        immediate = Dispatchers.Main.immediate,
    )

    @TestOnlyVisible
    fun mockDispatchersHolder(
        io: CoroutineDispatcher,
        default: CoroutineDispatcher,
        main: CoroutineDispatcher,
        immediate: CoroutineDispatcher,
    ) {
        holder = DispatchersHolder(
            io = io,
            default = default,
            main = main,
            immediate = immediate,
        )
    }

    fun io() = holder.io

    fun default() = holder.default

    fun main() = holder.main

    fun immediate() = holder.immediate
}

class DispatchersHolder(
    val io: CoroutineDispatcher,
    val default: CoroutineDispatcher,
    val main: CoroutineDispatcher,
    val immediate: CoroutineDispatcher,
)