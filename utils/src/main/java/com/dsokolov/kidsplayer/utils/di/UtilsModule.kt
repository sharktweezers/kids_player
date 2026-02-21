package com.dsokolov.kidsplayer.utils.di

import com.dsokolov.kidsplayer.utils.DispatchersProvider
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class UtilsModule {

    @Singleton
    @Provides
    fun provideDispatchersProvider(): DispatchersProvider {
        return DispatchersProvider(
            io = Dispatchers.IO,
            default = Dispatchers.Default,
            main = Dispatchers.Main,
            immediate = Dispatchers.Main.immediate
        )
    }
}