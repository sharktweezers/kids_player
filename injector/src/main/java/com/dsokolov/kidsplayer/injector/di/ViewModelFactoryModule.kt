package com.dsokolov.kidsplayer.injector.di

import androidx.lifecycle.ViewModelProvider
import com.dsokolov.kidsplayer.injector.scope.PerFeature
import dagger.Binds
import dagger.Module

/**
 * Переиспользуемый модуль для инициализации [DaggerViewModelFactory]
 * Должен использоваться во всех компонентах, где есть View Model
 */
@Module
abstract class ViewModelFactoryModule {
    @Binds
    @PerFeature
    abstract fun bindViewModelFactory(
        viewModelFactory: DaggerViewModelFactory,
    ): ViewModelProvider.Factory
}