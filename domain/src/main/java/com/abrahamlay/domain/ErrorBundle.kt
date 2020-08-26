package com.abrahamlay.domain

/**
 * Created by Abraham Lay on 24/08/20.
 */
interface ErrorBundle {
    val exception: Exception?
    val message: String?
}