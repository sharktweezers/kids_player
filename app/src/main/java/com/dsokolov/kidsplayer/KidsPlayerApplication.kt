package com.dsokolov.kidsplayer

import android.app.Application
import com.dsokolov.kidsplayer.di.AppDeps
import com.dsokolov.kidsplayer.di.Di
import com.dsokolov.kidsplayer.injector.di.ViewModelFactoryHolder

class KidsPlayerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initDi()
    }

    private fun initDi() {
        val appDependencies = object : AppDeps {
            override val application: KidsPlayerApplication
                get() = this@KidsPlayerApplication

        }
        Di.init(appDependencies)
        Di.getComponent().inject(ViewModelFactoryHolder.store)
    }
}