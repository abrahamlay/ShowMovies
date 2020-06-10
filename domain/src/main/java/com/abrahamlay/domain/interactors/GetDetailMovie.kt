package com.abrahamlay.domain.interactors

import com.abrahamlay.domain.FlowableUseCase
import com.abrahamlay.domain.PostExecutionThread
import com.abrahamlay.domain.entities.DetailMovieModel
import com.abrahamlay.domain.repositories.MovieRepository
import io.reactivex.Flowable

/**
 * Created by Abraham Lay on 10/06/20.
 */
class GetDetailMovie constructor(
    private val repository: MovieRepository,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<DetailMovieModel, GetDetailMovie.Params>(postExecutionThread) {
    override fun build(params: Params): Flowable<DetailMovieModel> {
        return repository.getMovieDetails(params.apiKey, params.movieId)
    }

    data class Params(val apiKey: String, val movieId: Int)
}