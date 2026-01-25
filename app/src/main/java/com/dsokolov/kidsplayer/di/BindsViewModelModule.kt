package com.dsokolov.kidsplayer.di

import com.dsokolov.kidsplayer.injector.di.ViewModelFactoryModule
import dagger.Module

/**
 *
 * Нужно прописывать или добавлять через include все вью модели,
 * Которые хотим инжектить НЕ через AssistedInject
 */
@Module(includes = [ViewModelFactoryModule::class])
abstract class BindsViewModelModule {
    /**
     * @Binds
     * @IntoMap
     * @ViewModelKey(VM::class)
     * abstract fun bindAnyViewModel(viewModel: YourViewModel): ViewModel
     */
}