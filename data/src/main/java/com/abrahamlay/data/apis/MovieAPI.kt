package com.abrahamlay.data.apis

import com.abrahamlay.data.dtos.GenresDto
import com.abrahamlay.data.dtos.MovieDto
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * Created by Abraham Lay on 2020-06-09.
 */
interface MovieAPI {
    @GET("3/discover/movie")
    fun getDiscoverMoviesByGenre(
        @Query("api_key") apiKey: String,
        @QueryMap map: HashMap<String, Any>
    ): Flowable<MovieDto>

    @GET("3/genre/movie/list")
    fun getGenres(@Query("api_key") apiKey: String): Flowable<GenresDto>
}