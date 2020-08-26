package com.abrahamlay.base.presentation

import androidx.lifecycle.ViewModel
import com.abrahamlay.base.subscriber.ResultState
import com.abrahamlay.base.util.getViewModelScope
import com.abrahamlay.domain.Either
import kotlinx.coroutines.CoroutineScope

/**
 * Created by Abraham Lay on 2019-10-06.
 */
abstract class BaseViewModel(coroutineScope: CoroutineScope?) : ViewModel() {

    val coroutineScope: CoroutineScope = getViewModelScope(coroutineScope)

    protected fun <T> Either<Throwable, T>.toResult() = when (this) {
        is Either.Error -> ResultState.Error<T>(
            this.error
        )
        is Either.Value -> ResultState.Success(
            this.value
        )
    }

    protected fun <T> Either<Throwable, T>.toPaginationResult(
        onResult: (T) -> Unit,
        onError: (Throwable?) -> Unit
    ) = when (this) {
        is Either.Error -> onError(this.error)

        is Either.Value -> onResult(this.value)
    }
}