package com.dsokolov.kidsplayer.injector

abstract class AbstractComponentHolder<Api : ComponentApi, Dependencies : ComponentDependencies> :
    AbstractInstanceHolder<Api, Dependencies>(), ComponentHolder<Api>