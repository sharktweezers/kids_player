package com.dsokolov.kidsplayer.injector

import java.lang.ref.WeakReference

abstract class AbstractWeakInstanceHolder<T, D> : InstanceHolder<T> {

    @Volatile
    private var reference: WeakReference<T>? = null

    protected val instance: T?
        get() = reference?.get()

    override fun get(): T = requireNotNull(instance) { "Instance not initialized" }

    override fun release() {
        reference = null
    }

    override fun isInitialized(): Boolean = instance != null

    fun init(dependencies: D): T {
        release()
        return getOrCreate(dependencies)
    }

    fun getOrCreate(dependencies: D): T = getOrCreate { dependencies }

    protected abstract fun initialize(dependencies: D): T

    protected open fun onInitialized(component: T, dependencies: D) {}

    internal fun getOrCreate(dependenciesProvider: () -> D): T {
        return instance ?: synchronized(this::class) {
            instance ?: dependenciesProvider.invoke().let { dependencies ->
                initialize(dependencies).also { newInstance ->
                    reference = WeakReference(newInstance)
                    onInitialized(newInstance, dependencies)
                }
            }
        }
    }

}