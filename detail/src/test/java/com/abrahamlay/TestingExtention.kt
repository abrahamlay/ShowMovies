package com.abrahamlay

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Created by Abraham Lay on 14/06/20.
 */


/**
 * Observes a [LiveData] until the `block` is done executing.
 */
fun <T> LiveData<T>.observeForTesting(block: () -> Unit) {
    val observer = Observer<T> { }
    try {
        observeForever(observer)
        block()
    } finally {
        removeObserver(observer)
    }
}