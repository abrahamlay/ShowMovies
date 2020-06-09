package com.abrahamlay.showmovies.module

import com.abrahamlay.domain.AndroidUIThread
import com.abrahamlay.domain.PostExecutionThread
import org.koin.dsl.module

/**
 * Created by Abraham Lay on 2020-06-09.
 */

val useCaseModule = module {
    single<PostExecutionThread> { return@single AndroidUIThread() }
    factory { GetPopularMovies(get(), get()) }
    factory { GetTopRatedMovies(get(), get()) }
}