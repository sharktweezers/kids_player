package com.dsokolov.kidsplayer.injector.test

import android.annotation.SuppressLint

@SuppressLint("ExperimentalAnnotationRetention")
@RequiresOptIn(message = "This part of the API is visible only for testing.")
annotation class TestOnlyVisible