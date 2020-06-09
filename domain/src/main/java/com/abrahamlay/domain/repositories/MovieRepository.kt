package com.abrahamlay.domain.repositories

import com.abrahamlay.domain.entities.GenreModel
import com.abrahamlay.domain.entities.MovieModel
import io.reactivex.Flowable

/**
 * Created by Abraham Lay on 2019-09-29.
 */

interface MovieRepository {
    fun getGenres(apiKey: String): Flowable<List<GenreModel>>
    fun getDiscoverMovies(apiKey: String, map: HashMap<String, Any>): Flowable<List<MovieModel>>
}