package com.dsokolov.kidsplayer.injector

abstract class AbstractInstanceHolder<T, D> : InstanceHolder<T> {

    @Volatile
    internal var instance: T? = null

    override fun get(): T = requireNotNull(instance) { "Instance not initialized" }

    override fun release() {
        instance?.also { released ->
            instance = null
            onReleased(released)
        }
    }

    override fun isInitialized(): Boolean = instance != null

    fun init(dependencies: D): T {
        release()
        return getOrCreate(dependencies)
    }

    fun getOrCreate(dependencies: D): T = getOrCreate { dependencies }

    protected abstract fun initialize(dependencies: D): T

    protected open fun onInitialized(component: T, dependencies: D) {}

    protected open fun onReleased(component: T) {}

    internal fun getOrCreate(dependenciesProvider: () -> D): T =
        instance ?: synchronized(this::class) {
            instance ?: dependenciesProvider.invoke().let { dependencies ->
                initialize(dependencies).also { newInstance ->
                    instance = newInstance
                    onInitialized(newInstance, dependencies)
                }
            }
        }

}