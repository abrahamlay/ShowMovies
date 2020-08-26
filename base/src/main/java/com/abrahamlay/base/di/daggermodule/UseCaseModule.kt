package com.abrahamlay.base.di.daggermodule


import com.abrahamlay.domain.AndroidUIThread
import com.abrahamlay.domain.PostExecutionThread
import com.abrahamlay.domain.interactors.*
import com.abrahamlay.domain.repositories.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Abraham Lay on 17/06/20.
 */
@Module
class UseCaseModule {

    @Provides
    fun provideGenreInteractor(
        movieRepository: MovieRepository
    ): GetGenresInteractor {
        return GetGenresInteractor(movieRepository)
    }

    @Provides
    fun provideDiscoverInteractor(
        movieRepository: MovieRepository
    ): GetDiscoverMoviesByGenre {
        return GetDiscoverMoviesByGenre(movieRepository)
    }

    @Provides
    fun provideGetReviewInteractor(
        movieRepository: MovieRepository
    ): GetReviews {
        return GetReviews(movieRepository)
    }

    @Provides
    fun provideDetailInteractor(
        movieRepository: MovieRepository
    ): GetDetailMovie {
        return GetDetailMovie(movieRepository)
    }

    @Provides
    fun provideGetVideosInteractor(
        movieRepository: MovieRepository
    ): GetVideos {
        return GetVideos(movieRepository)
    }
}