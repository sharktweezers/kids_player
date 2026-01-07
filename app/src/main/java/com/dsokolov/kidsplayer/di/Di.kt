package com.dsokolov.kidsplayer.di

import com.dsokolov.kidsplayer.injector.AbstractComponentHolder
import javax.inject.Inject

object Di : AbstractComponentHolder<AppApi, AppDeps>() {

    override fun initialize(dependencies: AppDeps): AppApi {
        return DaggerAppComponent
            .builder()
            .appDeps(dependencies)
            .build()
    }

    override fun onInitialized(component: AppApi, dependencies: AppDeps) {
        getComponent().inject(ComponentManager())
    }

    internal fun getComponent(): AppComponent = get() as AppComponent

    class ComponentManager {

        @Inject
        fun init(
            // add your features component holders
        ) {
            //This fores the component holders to initiate within their dagger modules
        }

    }

}