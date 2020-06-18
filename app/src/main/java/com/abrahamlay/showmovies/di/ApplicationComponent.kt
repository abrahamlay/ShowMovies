package com.abrahamlay.showmovies.di

import com.abrahamlay.base.di.component.BaseComponent
import com.abrahamlay.base.di.daggermodule.UseCaseModule
import com.abrahamlay.detail.internal.di.DetailModule
import com.abrahamlay.home.di.HomePageModule
import com.abrahamlay.showmovies.AndroidApplication
import com.abrahamlay.showmovies.ApplicationModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Abraham Lay on 17/06/20.
 */
@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        ApiModule::class,
        DataModule::class,
        UseCaseModule::class,
        HomePageModule::class,
        DetailModule::class
    ]
)
interface ApplicationComponent : BaseComponent {
    fun inject(androidApplication: AndroidApplication)
}