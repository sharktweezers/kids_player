package com.dsokolov.kidsplayer.utils.flow

import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch

private const val DEFAULT_WINDOW_DURATION = 500L

public fun <T> Flow<T>.throttleFirst(windowDuration: Long = DEFAULT_WINDOW_DURATION): Flow<T> {
    var job: Job = Job().apply { complete() }

    return onCompletion { job.cancel() }.run {
        flow {
            coroutineScope {
                collect { value ->
                    if (!job.isActive) {
                        emit(value)
                        job = launch { delay(windowDuration) }
                    }
                }
            }
        }
    }
}

fun <T> Flow<T>.throttleLatest(windowDuration: Long = DEFAULT_WINDOW_DURATION): Flow<T> {
    return this.conflate().transform { value ->
        emit(value)
        delay(windowDuration)
    }
}