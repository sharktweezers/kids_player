package com.dsokolov.kidsplayer.di

import com.dsokolov.kidsplayer.injector.di.ViewModelFactoryStore
import com.dsokolov.kidsplayer.presentation.PlayerViewModel
import com.dsokolov.kidsplayer.remote.di.ExternalBindsRepositoryModule
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        BindsViewModelModule::class,
        ExternalBindsRepositoryModule::class,
        PlayerUiModule::class,
    ],
    dependencies = [AppDeps::class],
)

@Singleton
interface AppComponent : AppApi {
    fun inject(componentManager: Di.ComponentManager)

    fun inject(componentManager: ViewModelFactoryStore)

    fun getPlayerViewModelFactory(): PlayerViewModel.Factory
}