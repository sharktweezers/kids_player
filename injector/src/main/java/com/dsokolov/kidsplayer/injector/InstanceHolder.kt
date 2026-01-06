package com.dsokolov.kidsplayer.injector

interface InstanceHolder<Instance> {

    fun get(): Instance

    fun release()

    fun isInitialized(): Boolean

}