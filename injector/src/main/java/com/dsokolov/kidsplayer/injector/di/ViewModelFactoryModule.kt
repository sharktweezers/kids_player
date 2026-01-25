package com.dsokolov.kidsplayer.injector.di

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Переиспользуемый модуль для инициализации [DaggerViewModelFactory]
 * Должен использоваться во всех компонентах, где есть View Model
 */
@Module(includes = [BindsEmptyViewModelModule::class])
class ViewModelFactoryModule {
    @Provides
    @Singleton
    fun bindViewModelFactory(
        viewModelsMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>,
    ): DaggerViewModelFactory = DaggerViewModelFactory(viewModelsMap)
}