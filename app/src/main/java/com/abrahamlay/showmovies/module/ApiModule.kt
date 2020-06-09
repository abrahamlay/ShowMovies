package com.abrahamlay.showmovies.module

import com.abrahamlay.showmovies.WebApiProvider
import com.abrahamlay.data.apis.MovieAPI
import org.koin.dsl.module

/**
 * Created by Abraham Lay on 2020-06-09.
 */

val apiModule = module {
    single { WebApiProvider }
    single {
        get<WebApiProvider>()
            .getRetrofit()
            .create(MovieAPI::class.java)
    }
}