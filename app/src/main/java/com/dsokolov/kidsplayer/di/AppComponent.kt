package com.dsokolov.kidsplayer.di

import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [AppModule::class, ViewModelModule::class],
    dependencies = [AppDeps::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        fun appsDependencies(deps: AppDeps): Builder
    }
}