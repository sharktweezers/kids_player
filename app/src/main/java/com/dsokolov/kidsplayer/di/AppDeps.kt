package com.dsokolov.kidsplayer.di

import com.dsokolov.kidsplayer.KidsPlayerApplication
import com.dsokolov.kidsplayer.injector.ComponentDependencies

interface AppDeps : ComponentDependencies {
    val application: KidsPlayerApplication
}