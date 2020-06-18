package com.abrahamlay.showmovies.di

import com.abrahamlay.base.webapi.WebApiProvider
import com.abrahamlay.showmovies.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Abraham Lay on 17/06/20.
 */
@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideWebApiProvider(): WebApiProvider {
        return WebApiProvider(BuildConfig.API_BASE_URL)
    }


}