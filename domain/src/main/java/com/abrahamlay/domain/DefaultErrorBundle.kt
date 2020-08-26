package com.abrahamlay.domain

/**
 * Created by Abraham Lay on 24/08/20.
 */
class DefaultErrorBundle(
    override val exception: Exception?,
    override val message: String = DEFAULT_ERROR_MESSAGE
) : Throwable(), ErrorBundle {
    companion object {
        const val DEFAULT_ERROR_MESSAGE =
            "Maaf, Terjadi masalah pada sistem kami, mohon coba kembali"
    }
}