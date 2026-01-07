package com.dsokolov.kidsplayer.di

import com.dsokolov.kidsplayer.PlayerActivity
import com.dsokolov.kidsplayer.injector.di.ViewModelFactoryModule
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        ViewModelFactoryModule::class
    ],
    dependencies = [AppDeps::class],
)

@Singleton
interface AppComponent : AppApi {
    fun inject(componentManager: Di.ComponentManager)
    fun inject(playerActivity: PlayerActivity)
}