package com.abrahamlay.base.subscriber

import io.reactivex.subscribers.DisposableSubscriber

abstract class DefaultSubscriber<T> : DisposableSubscriber<T>() {
    override fun onComplete() {
//        onError(ResultState.Error(Throwable("Cannot reach the server")))
    }

    override fun onNext(data: T) {
        onSuccess(ResultState.Success(data))
    }

    override fun onError(throwable: Throwable) {
        onError(ResultState.Error(throwable))
    }

    abstract fun onError(error: ResultState<T>)
 
    abstract fun onSuccess(data: ResultState<T>)
}