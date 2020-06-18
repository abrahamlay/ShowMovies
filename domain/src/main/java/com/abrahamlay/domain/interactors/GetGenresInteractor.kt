package com.abrahamlay.domain.interactors

import com.abrahamlay.domain.FlowableUseCase
import com.abrahamlay.domain.PostExecutionThread
import com.abrahamlay.domain.entities.GenreModel
import com.abrahamlay.domain.repositories.MovieRepository
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by Abraham Lay on 09/06/20.
 */

class GetGenresInteractor @Inject constructor(
    private val repository: MovieRepository,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<List<GenreModel>, GetGenresInteractor.Params>(postExecutionThread) {
    override fun build(params: Params): Flowable<List<GenreModel>> =
        repository.getGenres(params.apiKey)

    data class Params(val apiKey: String)
}