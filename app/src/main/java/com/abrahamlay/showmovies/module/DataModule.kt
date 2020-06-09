package com.abrahamlay.showmovies.module

import com.abrahamlay.data.repoimplementations.MovieRepositoryImpl
import com.abrahamlay.domain.repositories.MovieRepository
import org.koin.dsl.module

/**
 * Created by Abraham Lay on 2020-06-09.
 */

val dataModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get()) }
}