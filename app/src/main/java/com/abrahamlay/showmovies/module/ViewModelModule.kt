package com.abrahamlay.showmovies.module

import com.abrahamlay.base.subscriber.BaseViewModel
import com.abrahamlay.home.genre.GenreViewModel
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Abraham Lay on 2020-06-09.
 */

val viewModelModule = module {
    viewModel<GenreViewModel>()
    viewModel<BaseViewModel>()
}