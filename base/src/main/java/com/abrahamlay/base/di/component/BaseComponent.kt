package com.abrahamlay.base.di.component

import com.abrahamlay.domain.PostExecutionThread
import com.abrahamlay.domain.repositories.MovieRepository

/**
 * Created by Abraham Lay on 18/06/20.
 */

interface BaseComponent {
    fun repository(): MovieRepository
    fun postExecutionThread(): PostExecutionThread
}