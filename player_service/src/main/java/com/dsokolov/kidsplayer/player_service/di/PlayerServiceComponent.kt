package com.dsokolov.kidsplayer.player_service.di

import com.dsokolov.kidsplayer.injector.scope.PerFeature
import com.dsokolov.kidsplayer.player_service.KidsPlayerService
import dagger.Component

@Component(
    modules = [],
    dependencies = [PlayerServiceDeps::class],
)
@PerFeature
interface PlayerServiceComponent : PlayerServiceApi {
    fun inject(playerService: KidsPlayerService)
}