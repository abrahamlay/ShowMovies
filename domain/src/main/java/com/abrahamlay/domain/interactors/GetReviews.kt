package com.abrahamlay.domain.interactors

import com.abrahamlay.domain.FlowableUseCase
import com.abrahamlay.domain.PostExecutionThread
import com.abrahamlay.domain.entities.ReviewModel
import com.abrahamlay.domain.repositories.MovieRepository
import io.reactivex.Flowable

/**
 * Created by Abraham Lay on 10/06/20.
 */
class GetReviews constructor(
    private val repository: MovieRepository,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<List<ReviewModel>, GetReviews.Params>(postExecutionThread) {
    override fun build(params: Params): Flowable<List<ReviewModel>> {
        return repository.getReviews(params.apiKey, params.movieId, params.map)
    }

    data class Params(val apiKey: String, val movieId: Int, val map: HashMap<String, Any>) {
        companion object {
            const val PAGE_KEY = "page"
        }
    }
}