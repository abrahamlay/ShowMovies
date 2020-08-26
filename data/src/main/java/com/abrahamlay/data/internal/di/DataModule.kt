package com.abrahamlay.data.internal.di

import com.abrahamlay.data.apis.MovieAPI
import com.abrahamlay.data.repoimplementations.MovieRepositoryImpl
import com.abrahamlay.data.webapi.WebApiProvider
import com.abrahamlay.domain.repositories.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Abraham Lay on 17/06/20.
 */
@Module
class DataModule {


    @Provides
    @Singleton
    fun provideMovieAPI(webApiProvider: WebApiProvider): MovieAPI {
        // Whenever Dagger needs to provide an instance of type LoginRetrofitService,
        // this code (the one inside the @Provides method) is run.
        return webApiProvider.getRetrofit()
            .create(MovieAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(movieAPI: MovieAPI): MovieRepository {
        return MovieRepositoryImpl(movieAPI)
    }
}