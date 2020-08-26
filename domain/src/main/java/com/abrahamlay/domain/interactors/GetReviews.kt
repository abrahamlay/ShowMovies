package com.abrahamlay.domain.interactors

import com.abrahamlay.domain.CoroutineUseCase
import com.abrahamlay.domain.entities.ReviewModel
import com.abrahamlay.domain.repositories.MovieRepository
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by Abraham Lay on 10/06/20.
 */
class GetReviews @Inject constructor(
    private val repository: MovieRepository
) : CoroutineUseCase<List<ReviewModel>, GetReviews.Params>() {
    override suspend fun build(params: Params?): List<ReviewModel> {

        return repository.getReviews(
            params?.apiKey ?: throw Exception("Required apiKey"),
            params.movieId ?: throw Exception("Required movieId"),
            params.map ?: throw Exception("Required anyQuery")
        )
    }

    data class Params(val apiKey: String, val movieId: Int, val map: HashMap<String, Any>) {
        companion object {
            const val PAGE_KEY = "page"
        }
    }
}