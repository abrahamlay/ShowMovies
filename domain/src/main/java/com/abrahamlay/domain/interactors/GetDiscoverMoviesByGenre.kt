package com.abrahamlay.domain.interactors

import com.abrahamlay.domain.FlowableUseCase
import com.abrahamlay.domain.PostExecutionThread
import com.abrahamlay.domain.entities.MovieModel
import com.abrahamlay.domain.repositories.MovieRepository
import io.reactivex.Flowable
import javax.inject.Inject


/**
 * Created by Abraham Lay on 2019-09-29.
 */
class GetDiscoverMoviesByGenre @Inject constructor(
    private val repository: MovieRepository,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<List<MovieModel>, GetDiscoverMoviesByGenre.Params>(postExecutionThread) {
    override fun build(params: Params): Flowable<List<MovieModel>> {
        return repository.getDiscoverMovies(params.apiKey, params.map)
    }

    data class Params(val apiKey: String, val map: HashMap<String, Any>) {
        companion object {
            const val PAGE_KEY = "page"
            const val GENRE_KEY = "with_genres"
        }
    }
}