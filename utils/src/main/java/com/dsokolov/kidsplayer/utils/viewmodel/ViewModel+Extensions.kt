package com.dsokolov.kidsplayer.utils.viewmodel

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras

/**
 * Обертка над [androidx.fragment.app.viewModels],
 * которая в случае создания нового инстанса [VM] выполнит переданную лямбду [onViewModelCreate]
 * c новым инстансом в аргументах.
 * Если же инстанс [VM] будет получен из стора, то ничего вызвано не будет, лишь вернется инстанс.
 *
 * Тем самым эта обертка позволяет безопасно инициализировать `viewModel`.
 *
 * @param onViewModelCreate лямбда которая будет выполнена в случае создания новой `viewModel`
 * @param factoryProducer провайдер [ViewModelProvider.Factory] используемой для создания `viewModel`
 */
public inline fun <reified VM : ViewModel> Fragment.viewModelsStateAware(
    noinline onViewModelCreate: (VM) -> Unit,
    crossinline factoryProducer: () -> ViewModelProvider.Factory,
): Lazy<VM> {
    var cached: VM? = null
    val delegate: Lazy<VM> = viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return factoryProducer().create(modelClass, CreationExtras.Empty)
                    .also { viewModel ->
                        viewModel as VM
                        cached = viewModel
                        onViewModelCreate.invoke(viewModel)
                    }
            }
        }
    }
    return object : Lazy<VM> {
        override val value: VM
            get() = cached ?: delegate.value.also { viewModel -> cached = viewModel }

        override fun isInitialized(): Boolean = cached != null
    }
}

/**
 * Делегат для создания [ViewModel] с runtime параметрами в конструкторе
 *
 * Пример создания [ViewModel] с использованием делегата
 *
 * ```
 * class MyViewModel @AssistedInject constructor(
 *         @Assisted val id: Int,
 *         val repository: MyRepository
 * ) : BaseViewModel() {
 *
 *     @AssistedFactory
 *     interface Factory {
 *         fun create(@Assisted id: Int): MyViewModel
 *     }
 * }
 *
 * class MyFragment : BaseFragment() {
 *     private val myId by unsafeLazy { requireArguments().getInt("KEY") }
 *
 *     @Inject
 *     lateinit var viewModelFactory: MyViewModel.Factory
 *     private val viewModel: MyViewModel by viewModelsWithRuntimeArgs {
 *         viewModelFactory.create(id = myId)
 *     }
 * }
 * ```
 */
@Suppress("UNCHECKED_CAST")
public inline fun <reified T : ViewModel> Fragment.viewModelsWithRuntimeArgs(
    crossinline ownerProducer: () -> ViewModelStoreOwner = { this },
    crossinline creator: () -> T
): Lazy<T> {
    return createViewModelLazy(
        viewModelClass = T::class,
        storeProducer = {
            ownerProducer().viewModelStore
        },
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return creator.invoke() as T
                }
            }
        }
    )
}

@Suppress("UNCHECKED_CAST")
public inline fun <reified T : ViewModel> ComponentActivity.viewModelsWithRuntimeArgs(
    crossinline creator: () -> T
): Lazy<T> {
    return viewModels(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return creator.invoke() as T
                }
            }
        }
    )
}


