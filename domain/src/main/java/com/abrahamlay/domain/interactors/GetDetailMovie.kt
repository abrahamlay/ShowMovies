package com.abrahamlay.domain.interactors

import com.abrahamlay.domain.CoroutineUseCase
import com.abrahamlay.domain.PostExecutionThread
import com.abrahamlay.domain.entities.DetailMovieModel
import com.abrahamlay.domain.repositories.MovieRepository
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by Abraham Lay on 10/06/20.
 */
class GetDetailMovie @Inject constructor(
    private val repository: MovieRepository
) : CoroutineUseCase<DetailMovieModel, GetDetailMovie.Params>() {

    data class Params(val apiKey: String, val movieId: Int)

    override suspend fun build(params: Params?): DetailMovieModel {
        return repository.getMovieDetails(params?.apiKey ?: throw Exception("Required anyQuery"), params.movieId ?: throw Exception("Required movieId"))
    }
}