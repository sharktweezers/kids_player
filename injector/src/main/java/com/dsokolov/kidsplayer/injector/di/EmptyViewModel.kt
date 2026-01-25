package com.dsokolov.kidsplayer.injector.di

import androidx.lifecycle.ViewModel
import javax.inject.Inject

// Костыль чтобы не ловить креш если у нас нет ни одной VM создаваемой без AssistedFactory
// И javax.inject.Provider<androidx.lifecycle.ViewModel>> пустая
internal class EmptyViewModel @Inject constructor(): ViewModel()