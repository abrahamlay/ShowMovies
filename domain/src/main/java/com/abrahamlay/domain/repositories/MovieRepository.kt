package com.abrahamlay.domain.repositories

import com.abrahamlay.domain.entities.*
import io.reactivex.Flowable

/**
 * Created by Abraham Lay on 2019-09-29.
 */

interface MovieRepository {
    fun getGenres(apiKey: String): Flowable<List<GenreModel>>
    fun getDiscoverMovies(apiKey: String, map: HashMap<String, Any>): Flowable<List<MovieModel>>
    fun getReviews(
        apiKey: String,
        movieId: Int,
        map: HashMap<String, Any>
    ): Flowable<List<ReviewModel>>

    fun getVideo(
        apiKey: String,
        movieId: Int
    ): Flowable<List<VideoModel>>

    fun getMovieDetails(
        apiKey: String,
        movieId: Int
    ): Flowable<DetailMovieModel>

}