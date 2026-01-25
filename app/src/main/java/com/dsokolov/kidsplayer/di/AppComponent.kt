package com.dsokolov.kidsplayer.di

import com.dsokolov.kidsplayer.injector.di.ViewModelFactoryModule
import com.dsokolov.kidsplayer.presentation.PlayerViewModel
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

    fun getPlayerViewModelFactory(): PlayerViewModel.Factory
}