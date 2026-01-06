package com.dsokolov.kidsplayer.injector

abstract class AbstractLazyComponentHolder<Api : ComponentApi, Dependencies : ComponentDependencies> :
    AbstractLazyInstanceHolder<Api, Dependencies>(), ComponentHolder<Api>