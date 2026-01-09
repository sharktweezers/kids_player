package com.dsokolov.kidsplayer.mvi_core

/**
 * Фабрика полностью готовой обобщенная логики над стейт-машиной [StateMachine],
 * уже связанной с логикой обработки ее команд [CommandHandler].
 *
 * @see Store
 * @see StateMachine
 * @see CommandHandler
 */
object StoreFactory {

    /**
     * Создает стейт-машину логики [StateMachine], связывает ее с обработчиком команд [commandHandler]
     * и возвращает [Store] интерфейс для взаимодействия с обобщенной логикой.
     *
     * Так же получает начальные команды и выполняет их в [commandHandler].
     *
     * @param initialState начальное состояния стейт-машины
     * @param initialCommands начальные команды
     * @param stateUpdater редюсер состояния стейт-машины
     * @param commandHandler обработчик команд испускаемых стейт-машиной
     * @param transitionListener слушатель изменний состояния стейт-машины
     */
    fun <Event, State, SideEffect, Command> createStore(
            stateUpdater: Reducer<Event, State, SideEffect, Command>,
            commandHandler: CommandHandler<Event, Command>,
            initialState: State,
            initialCommands: List<Command>,
            transitionListener: TransitionListener<Event, State, SideEffect, Command>? = null
    ): Store<Event, State, SideEffect> {
        val stateMachine: StateMachine<Event, State, SideEffect, Command> = StateMachine(
                stateUpdater,
                initialState,
        )

        return StoreImpl(
                stateMachine = stateMachine,
                commandHandler = commandHandler,
                initialCommands = initialCommands,
                transitionListener = transitionListener
        )
    }
}