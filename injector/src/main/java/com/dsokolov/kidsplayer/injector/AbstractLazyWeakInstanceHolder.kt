package com.dsokolov.kidsplayer.injector

abstract class AbstractLazyWeakInstanceHolder<T, D> : AbstractWeakInstanceHolder<T, D>() {

    private var dependenciesProvider: (() -> D)? = null

    fun initDependenciesProvider(provider: () -> D) {
        dependenciesProvider = provider
        release()
    }

    fun clearDependenciesProvider() {
        dependenciesProvider = null
    }

    override fun get(): T = instance ?: getOrCreate(requireDependenciesProvider())

    private fun requireDependenciesProvider(): () -> D = requireNotNull(dependenciesProvider) {
        "Dependencies provider ${this::class.simpleName} is not initialized"
    }

}
