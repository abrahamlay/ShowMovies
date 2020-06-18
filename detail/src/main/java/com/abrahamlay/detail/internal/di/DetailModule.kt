package com.abrahamlay.detail.internal.di

import com.abrahamlay.detail.DetailViewModel
import com.abrahamlay.detail.reviews.ReviewViewModel
import com.abrahamlay.detail.video.VideoViewModel
import com.abrahamlay.domain.interactors.GetDetailMovie
import com.abrahamlay.domain.interactors.GetReviews
import com.abrahamlay.domain.interactors.GetVideos
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Abraham Lay on 18/06/20.
 */
@Module
class DetailModule {
    @Provides
    @Singleton
    fun provideReviewVM(interactor: GetReviews): ReviewViewModel {
        return ReviewViewModel(interactor)
    }

    @Provides
    @Singleton
    fun provideDetailMovieVM(interactor: GetDetailMovie): DetailViewModel {
        return DetailViewModel(interactor)
    }

    @Provides
    @Singleton
    fun provideGetVideoVM(interactor: GetVideos): VideoViewModel {
        return VideoViewModel(interactor)
    }
}