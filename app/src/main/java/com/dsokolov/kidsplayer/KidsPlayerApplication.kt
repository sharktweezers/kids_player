package com.dsokolov.kidsplayer

import android.app.Application
import android.content.Context
import com.dsokolov.kidsplayer.di.AppComponent
import com.dsokolov.kidsplayer.di.AppDeps
import com.dsokolov.kidsplayer.di.DaggerAppComponent

class KidsPlayerApplication : Application() {
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appsDependencies(object: AppDeps {
            override val context = this@KidsPlayerApplication

        }).build()
    }
}