package com.abrahamlay.showmovies.navigation

import com.abrahamlay.base.navigation.CommonNavigation
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Abraham Lay on 17/06/20.
 */

@Module
class NavigationModule {

    @Singleton
    @Provides
    fun provideCommonNavigation(): CommonNavigation {
        return CommonNavigationImplementation()
    }
}