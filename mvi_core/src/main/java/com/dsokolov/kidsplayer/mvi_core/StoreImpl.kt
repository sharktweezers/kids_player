package com.dsokolov.kidsplayer.mvi_core

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.launch

internal class StoreImpl<in Event, out State, out SideEffect, out Command>(
        private val stateMachine: StateMachine<Event, State, SideEffect, Command>,
        private val commandHandler: CommandHandler<Event, Command>,
        private val initialCommands: List<Command>,
        private val transitionListener: TransitionListener<Event, State, SideEffect, Command>? = null
) : Store<Event, State, SideEffect> {

    private val transitionSourceStarted = CompletableDeferred<Unit>()
    private val stateSourceStarted = CompletableDeferred<Unit>()
    private val commandSourceStarted = CompletableDeferred<Unit>()
    private val sideEffectSourceStarted = CompletableDeferred<Unit>()
    private val commandMessageSourceStarted = CompletableDeferred<Unit>()

    override suspend fun onEvent(event: Event) {
        stateSourceStarted.join()
        stateMachine.onEvent(event)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun start(
            coroutineScope: CoroutineScope,
            coroutineDispatcher: CoroutineDispatcher,
            actionState: suspend (State) -> Unit,
            actionSideEffect: suspend (SideEffect) -> Unit,
            coroutineExceptionHandler: CoroutineExceptionHandler?,
    ) {
        val errorHandler = coroutineExceptionHandler ?: CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }

        coroutineScope.launch(coroutineDispatcher + errorHandler) {

            /*if (transitionListener != null) {
                stateMachine.getTransitionSource()
                        .onSubscription {
                            transitionSourceStarted.complete(Unit)
                        }
                        .onEach { transitionListener.onTransition(it) }
                        .launchIn(coroutineScope)
            } else {
                transitionSourceStarted.complete(Unit)
            }*/

            commandHandler.getEventSource()
                    .onStart {
                        commandMessageSourceStarted.complete(Unit)
                    }
                    .onEach(stateMachine::onEvent)
                    .launchIn(this)

            // TODO дописать stateMachine
            /*stateMachine.getSideEffectSource()
                    .onSubscription {
                        sideEffectSourceStarted.complete(Unit)
                    }
                    .flatMapMerge { it.asFlow().cancellable() }
                    .onEach(actionSideEffect)
                    .launchIn(coroutineScope)

            stateMachine.getCommandSource()
                    .onSubscription {
                        commandSourceStarted.complete(Unit)
                    }
                    .flatMapMerge { it.asFlow().cancellable() }
                    .onEach(commandHandler::onCommand)
                    .launchIn(coroutineScope)

            stateMachine.getStateSource(
                    onSubscription = {
                        transitionSourceStarted.join()
                        sideEffectSourceStarted.join()
                        commandMessageSourceStarted.join()
                        commandSourceStarted.join()
                        stateSourceStarted.complete(Unit)
                    }
            )
                    .onEach(actionState)
                    .launchIn(this)

            stateSourceStarted.join()
            stateMachine.emitInitialCommands(initialCommands)*/
        }
    }

}