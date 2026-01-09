import com.dsokolov.kidsplayer.mvi_core.CommandHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOn

@OptIn(ExperimentalCoroutinesApi::class)
public open class DefaultCommandHandler<out Event, in Command> : CommandHandler<Event, Command> {

    private val commands = MutableSharedFlow<Command>()

    final override fun getEventSource(): Flow<Event> {
        return commands
            .flatMapMerge { command -> handleCommand(command) }
            .flowOn(Dispatchers.Default)
    }

    final override suspend fun onCommand(command: Command) {
        commands.emit(command)
    }

    protected open fun handleCommand(command: Command): Flow<Event> = emptyFlow()

}