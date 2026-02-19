# Kids Player
Приложение плеер детских песен:

1 Архитектура и используемые подходы:

 - Kotlin 2.1
 - Gradle kts + libs.versions.toml + convenient plugin (реализован как build-common folder)
 - DI dagger2 многомодульность api/impl с передачей зависимостей через dependencies
 - Многопоточность coroutines + flow
 - Экран Compose + ViewModel (возможность инжекта через Assisted или через Dagger singleton factory)
 - Архитектура MVI tea подход c реалиованной под капотом (state machine)
 - Работа плеера через ForegroundService и связкой состояния плеера через единый state в PlayerInteractor

2 Видео:

https://github.com/sharktweezers/kids_player/player_demo.mp4