package com.abrahamlay.home.discover

import com.abrahamlay.domain.entities.MovieModel

interface DiscoverDataSourceDelegate<Data> {

    fun getDiscoverMovieByPageData(
        genreId: Int,
        pagePosition: Int,
        onResult: (List<Data>) -> Unit,
        onErrorRequest: (Throwable?) -> Unit
    )
}
