package com.dsokolov.kidsplayer.player_service.di

import com.dsokolov.kidsplayer.injector.AbstractLazyWeakInstanceHolder

private typealias InstanceHolder = AbstractLazyWeakInstanceHolder<PlayerServiceApi, PlayerServiceDeps>

object PlayerServiceComponentHolder : InstanceHolder() {

    override fun initialize(dependencies: PlayerServiceDeps): PlayerServiceApi {
        return DaggerPlayerServiceComponent
            .builder()
            .playerServiceDeps(dependencies)
            .build()
    }

    internal fun getComponent(): PlayerServiceComponent = get() as PlayerServiceComponent
}