package com.abrahamlay.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

/**
 * Created by Abraham Lay on 24/08/20.
 */

abstract class CoroutineUseCase<Entity, Params>() {

    var params: Params? = null

    fun addParam(param: Params?) = apply {
        this.params = param
    }

    abstract suspend fun build(param: Params?): Entity

    suspend fun execute(scope: CoroutineScope) = withContext(scope.coroutineContext) {
        either { build(params) }
    }
}