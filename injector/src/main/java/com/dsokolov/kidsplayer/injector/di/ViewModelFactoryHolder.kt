package com.dsokolov.kidsplayer.injector.di

import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

object ViewModelFactoryHolder {
    val store = ViewModelFactoryStore()
    fun factory() = store.viewModelFactory
}

class ViewModelFactoryStore internal constructor() {
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
        private set

    @Inject
    fun onInject(factory: DaggerViewModelFactory) {
        viewModelFactory = factory
    }
}