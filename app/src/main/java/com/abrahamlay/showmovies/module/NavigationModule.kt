package com.abrahamlay.showmovies.module

import com.abrahamlay.base.navigation.CommonNavigation
import com.abrahamlay.domain.AndroidUIThread
import com.abrahamlay.domain.PostExecutionThread
import com.abrahamlay.domain.interactors.*
import com.abrahamlay.showmovies.navigation.CommonNavigationImplementation
import org.koin.dsl.module

/**
 * Created by Abraham Lay on 2020-06-09.
 */

val navigationModule = module {
    single<CommonNavigation> { return@single CommonNavigationImplementation() }
}