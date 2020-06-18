package com.abrahamlay.showmovies

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by Abraham Lay on 18/06/20.
 */

@Module
class ApplicationModule {
    private var app: AndroidApplication? = null

    @Provides
    @Singleton
    fun provideApplicationContext(): Context? {
        return app
    }
}