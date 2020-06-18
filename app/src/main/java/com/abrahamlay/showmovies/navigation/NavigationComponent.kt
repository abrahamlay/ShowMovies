package com.abrahamlay.showmovies.navigation

import com.abrahamlay.base.di.component.BaseNavigationComponent
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Abraham Lay on 17/06/20.
 */

@Singleton
@Component(modules = [NavigationModule::class])
interface NavigationComponent :
    BaseNavigationComponent {

}