package com.abrahamlay.showmovies.module

import com.abrahamlay.detail.DetailViewModel
import com.abrahamlay.detail.reviews.ReviewViewModel
import com.abrahamlay.detail.video.VideoViewModel
import com.abrahamlay.home.discover.DiscoverViewModel
import com.abrahamlay.home.genre.GenreViewModel
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Abraham Lay on 2020-06-09.
 */

val viewModelModule = module {
    viewModel<GenreViewModel>()
    viewModel<DiscoverViewModel>()
    viewModel<ReviewViewModel>()
    viewModel<VideoViewModel>()
    viewModel<DetailViewModel>()
}