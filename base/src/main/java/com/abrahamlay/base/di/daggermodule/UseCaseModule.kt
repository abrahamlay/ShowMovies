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
    @Singleton
    fun provideExecutor(): PostExecutionThread {
        return AndroidUIThread()
    }

    @Provides
    fun provideGenreInteractor(
        movieRepository: MovieRepository,
        postExecutionThread: PostExecutionThread
    ): GetGenresInteractor {
        return GetGenresInteractor(movieRepository, postExecutionThread)
    }

    @Provides
    fun provideDiscoverInteractor(
        movieRepository: MovieRepository,
        postExecutionThread: PostExecutionThread
    ): GetDiscoverMoviesByGenre {
        return GetDiscoverMoviesByGenre(movieRepository, postExecutionThread)
    }

    @Provides
    fun provideGetReviewInteractor(
        movieRepository: MovieRepository,
        postExecutionThread: PostExecutionThread
    ): GetReviews {
        return GetReviews(movieRepository, postExecutionThread)
    }

    @Provides
    fun provideDetailInteractor(
        movieRepository: MovieRepository,
        postExecutionThread: PostExecutionThread
    ): GetDetailMovie {
        return GetDetailMovie(movieRepository, postExecutionThread)
    }

    @Provides
    fun provideGetVideosInteractor(
        movieRepository: MovieRepository,
        postExecutionThread: PostExecutionThread
    ): GetVideos {
        return GetVideos(movieRepository, postExecutionThread)
    }
}