package com.abrahamlay.data.apis

import com.abrahamlay.data.dtos.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * Created by Abraham Lay on 2020-06-09.
 */
interface MovieAPI {
    @GET("3/discover/movie")
    suspend fun getDiscoverMoviesByGenre(
        @Query("api_key") apiKey: String,
        @QueryMap map: HashMap<String, Any>
    ): MovieDto

    @GET("3/genre/movie/list")
    suspend fun getGenres(@Query("api_key") apiKey: String): GenresDto

    @GET("3/movie/{movieId}/reviews")
    suspend fun getReviews(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String,
        @QueryMap map: HashMap<String, Any>
    ): ReviewDto

    @GET("3/movie/{movieId}/videos")
    suspend fun getVideo(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String
    ): VideoDto

    @GET("3/movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String
    ): DetailMovieDto
}