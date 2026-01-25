package com.dsokolov.kidsplayer.injector.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface BindsEmptyViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(EmptyViewModel::class)
    fun bindEmptyViewModel(viewModel: EmptyViewModel): ViewModel
}