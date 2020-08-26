package com.abrahamlay.data.repoimplementations

import com.abrahamlay.data.apis.MovieAPI
import com.abrahamlay.data.mapper.map
import com.abrahamlay.domain.entities.*
import com.abrahamlay.domain.repositories.MovieRepository
import javax.inject.Inject

/**
 * Created by Abraham Lay on 2020-06-09.
 */


class MovieRepositoryImpl @Inject constructor(private val api: MovieAPI) : MovieRepository {
    override suspend fun getGenres(apiKey: String): List<GenreModel> = api.getGenres(apiKey).map()


    override suspend fun getDiscoverMovies(
        apiKey: String,
        map: HashMap<String, Any>
    ): List<MovieModel> = api.getDiscoverMoviesByGenre(apiKey, map).map()

    override suspend fun getReviews(
        apiKey: String,
        movieId: Int,
        map: HashMap<String, Any>
    ): List<ReviewModel> = api.getReviews(movieId, apiKey, map).map()

    override suspend fun getVideo(apiKey: String, movieId: Int): List<VideoModel> =
        api.getVideo(movieId, apiKey).map()


    override suspend fun getMovieDetails(apiKey: String, movieId: Int): DetailMovieModel =
        api.getMovieDetails(movieId, apiKey).map()

}