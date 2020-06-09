package com.abrahamlay.domain

import io.reactivex.Scheduler

interface PostExecutionThread {
    fun getScheduler(): Scheduler
}