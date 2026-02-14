package com.dsokolov.kidsplayer.utils.flow

import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.cancellation.CancellationException

private const val DEFAULT_WINDOW_DURATION = 500L

fun <T> Flow<T>.throttleFirst(windowDuration: Long = DEFAULT_WINDOW_DURATION): Flow<T> {
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

fun <A, B: Any, R> Flow<A>.withLatestFrom(other: Flow<B>, transform: suspend (A, B) -> R): Flow<R> = flow {
    coroutineScope {
        val latestB = AtomicReference<B?>()
        val outerScope = this
        launch {
            try {
                other.collect { latestB.set(it) }
            } catch(e: CancellationException) {
                outerScope.cancel(e) // cancel outer scope on cancellation exception, too
            }
        }
        collect { a: A ->
            latestB.get()?.let { b -> emit(transform(a, b)) }
        }
    }
}