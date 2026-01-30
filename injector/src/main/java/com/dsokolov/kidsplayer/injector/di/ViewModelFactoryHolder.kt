package com.dsokolov.kidsplayer.injector.di

import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

object ViewModelFactoryHolder {
    val store = ViewModelFactoryStore()
    fun factory() = store.viewModelFactory
}

class ViewModelFactoryStore internal constructor() {
    @Volatile
    internal var viewModelFactory: ViewModelProvider.Factory? = null
        private set

    @Inject
    fun onInject(factory: DaggerViewModelFactory) {
        if (viewModelFactory == null) {
            viewModelFactory = factory
        }
    }
}