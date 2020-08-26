package com.abrahamlay.base.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope

/**
 * Created by Abraham Lay on 26/08/20.
 */

fun ViewModel.getViewModelScope(coroutineScope: CoroutineScope?) =
    coroutineScope
        ?: this.viewModelScope
