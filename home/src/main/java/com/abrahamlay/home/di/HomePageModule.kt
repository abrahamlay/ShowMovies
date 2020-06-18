package com.abrahamlay.home.di

import com.abrahamlay.domain.interactors.GetDiscoverMoviesByGenre
import com.abrahamlay.domain.interactors.GetGenresInteractor
import com.abrahamlay.home.discover.DiscoverViewModel
import com.abrahamlay.home.genre.GenreViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Abraham Lay on 18/06/20.
 */
@Module
class HomePageModule {

    @Provides
    @Singleton
    fun provideGenreVM(interactor: GetGenresInteractor): GenreViewModel {
        return GenreViewModel(interactor)
    }

    @Provides
    @Singleton
    fun provideDiscoverVM(interactor: GetDiscoverMoviesByGenre): DiscoverViewModel {
        return DiscoverViewModel(interactor)
    }
}