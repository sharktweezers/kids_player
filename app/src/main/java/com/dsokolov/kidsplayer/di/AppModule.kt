package com.dsokolov.kidsplayer.di

import android.content.Context
import com.dsokolov.kidsplayer.KidsPlayerApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideContext(kidsPlayerApplication: KidsPlayerApplication): Context {
        return kidsPlayerApplication.applicationContext
    }
}