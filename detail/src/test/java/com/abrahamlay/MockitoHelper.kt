package com.abrahamlay

import org.mockito.Mockito

object MockitoHelper {
    @JvmStatic
    fun <T> anyObject(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    @JvmStatic
    fun <T> eq(value: T): T {
        Mockito.eq<T>(value)
        return uninitialized()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> uninitialized(): T = null as T
}