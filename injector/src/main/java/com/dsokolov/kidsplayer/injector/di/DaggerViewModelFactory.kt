package com.dsokolov.kidsplayer.injector.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Provider

/**
 * Фабрика, нужная для создания [ViewModelProvider.Factory] любой [ViewModel], у которой @Inject constructor
 *
 * Пример использования ViewModelFactory во фрагментах:
 * ```kotlin
 *     @Inject
 *     lateinit var viewModelFactory: ViewModelProvider.Factory
 *
 *     private val viewModel: SomeViewModel by viewModels { viewModelFactory }
 * ```
 */
@Suppress("UNCHECKED_CAST")
class DaggerViewModelFactory(
    private val viewModelsMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = viewModelsMap[modelClass] ?: viewModelsMap.asIterable().firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")

        return try {
            creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}