package com.abrahamlay.domain

import retrofit2.HttpException

/**
 * Created by Abraham Lay on 24/08/20.
 */

sealed class Either<out E, out V> {
    data class Value<out V>(val value: V) : Either<Nothing, V>()
    data class Error<out E>(val error: Throwable) : Either<E, Nothing>()
}

suspend fun <V> either(block: suspend () -> V): Either<Throwable, V> = runCatching {
    Either.Value(block())
}.getOrElse {
    if (BuildConfig.DEBUG) {
        it.printStackTrace()
    }

    if (it is Exception) {
        if (it is HttpException) {
            when {
                it.code() == 404 -> {
                    Either.Error(
                        DefaultErrorBundle(
                            it,
                            "Sistem tidak ditemukan"
                        )
                    )
                }
                else -> {
                    Either.Error(
                        DefaultErrorBundle(it)
                    )
                }
            }
        } else {
            Either.Error(
                DefaultErrorBundle(it)
            )
        }
    } else {
        Either.Error(
            DefaultErrorBundle(null)
        )
    }
}

