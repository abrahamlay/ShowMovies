package com.abrahamlay.base.subscriber

import androidx.lifecycle.ViewModel
import com.abrahamlay.domain.Either

/**
 * Created by Abraham Lay on 2019-10-06.
 */
abstract class BaseViewModel : ViewModel() {
    protected fun <T> Either<Throwable, T>.toResult() = when (this) {
        is Either.Error -> ResultState.Error<T>(this.error)
        is Either.Value -> ResultState.Success(this.value)
    }

    protected fun <T> Either<Throwable, T>.toPaginationResult(
        onResult: (T) -> Unit,
        onError: (Throwable?) -> Unit
    ) = when (this) {
        is Either.Error -> onError(this.error)

        is Either.Value -> onResult(this.value)
    }
}