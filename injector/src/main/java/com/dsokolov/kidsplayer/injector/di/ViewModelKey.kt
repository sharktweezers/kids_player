package com.dsokolov.kidsplayer.injector.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Использовать для всех ViewModel у которых @Inject constructor
 *
 * Пример использования:
 *  @Module
 *  abstract class SomeModule {
 *      @Binds
 *      @IntoMap
 *      @ViewModelKey(SomeViewModel::class)
 *      abstract fun bindSomeViewModel(viewModel: SomeViewModel): ViewModel
 *   }
 *
 */
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)