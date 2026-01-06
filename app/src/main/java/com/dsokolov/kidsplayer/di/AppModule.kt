package com.dsokolov.kidsplayer.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun appContext(appDeps: AppDeps): Context {
        return appDeps.context
    }
}