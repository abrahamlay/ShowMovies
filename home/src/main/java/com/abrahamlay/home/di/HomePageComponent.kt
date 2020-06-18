package com.abrahamlay.home.di

import com.abrahamlay.base.di.component.BaseComponent
import com.abrahamlay.base.di.component.BaseNavigationComponent
import com.abrahamlay.home.discover.DiscoverMovieFragment
import com.abrahamlay.home.genre.MainFragment
import dagger.Component

/**
 * Created by Abraham Lay on 17/06/20.
 */

@Component(
    dependencies = [
        BaseComponent::class,
        BaseNavigationComponent::class
    ]
)
interface HomePageComponent {
    fun inject(mainFragment: MainFragment)
    fun inject(discoverMovieFragment: DiscoverMovieFragment)
}