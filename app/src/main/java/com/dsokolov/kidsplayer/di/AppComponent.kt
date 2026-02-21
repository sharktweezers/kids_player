package com.dsokolov.kidsplayer.di

import com.dsokolov.kidsplayer.injector.di.ViewModelFactoryStore
import com.dsokolov.kidsplayer.presentation.PlayerViewModel
import com.dsokolov.kidsplayer.remote.di.ExternalBindsRepositoryModule
import com.dsokolov.kidsplayer.domain.di.DomainModule
import com.dsokolov.kidsplayer.di.internal_modules.InternalModules
import com.dsokolov.kidsplayer.utils.di.UtilsModule
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        UtilsModule::class,
        AppModule::class,
        InternalModules::class,
        DomainModule::class,
        BindsViewModelModule::class,
        ExternalBindsRepositoryModule::class,
        PlayerUiModule::class,
    ],
    dependencies = [AppDeps::class],
)
@Singleton
internal interface AppComponent : AppApi {

    fun inject(componentManager: Di.ComponentManager)

    fun inject(componentManager: ViewModelFactoryStore)

    fun getPlayerViewModelFactory(): PlayerViewModel.Factory
}