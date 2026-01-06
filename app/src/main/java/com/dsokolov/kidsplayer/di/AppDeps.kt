package com.dsokolov.kidsplayer.di

import android.content.Context
import com.dsokolov.kidsplayer.injector.Deps

interface AppDeps : Deps {
    val context: Context
}