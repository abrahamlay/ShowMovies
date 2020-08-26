package com.abrahamlay.domain.repositories

import com.abrahamlay.domain.entities.*
import io.reactivex.Flowable

/**
 * Created by Abraham Lay on 2019-09-29.
 */

interface MovieRepository {
    suspend fun getGenres(apiKey: String): List<GenreModel>
    suspend fun getDiscoverMovies(apiKey: String, map: HashMap<String, Any>): List<MovieModel>
    suspend fun getReviews(
        apiKey: String,
        movieId: Int,
        map: HashMap<String, Any>
    ): List<ReviewModel>

    suspend fun getVideo(
        apiKey: String,
        movieId: Int
    ): List<VideoModel>

    suspend fun getMovieDetails(
        apiKey: String,
        movieId: Int
    ): DetailMovieModel
}