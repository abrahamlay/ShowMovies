package com.abrahamlay.domain.interactors

import com.abrahamlay.domain.CoroutineUseCase
import com.abrahamlay.domain.entities.MovieModel
import com.abrahamlay.domain.repositories.MovieRepository
import java.lang.Exception
import javax.inject.Inject


/**
 * Created by Abraham Lay on 2019-09-29.
 */
class GetDiscoverMoviesByGenre @Inject constructor(
    private val repository: MovieRepository
) : CoroutineUseCase<List<MovieModel>, GetDiscoverMoviesByGenre.Params>() {

    override suspend fun build(param: Params?): List<MovieModel> {
        return repository.getDiscoverMovies(param?.apiKey ?: throw Exception("Required apiKey"), param.map ?: throw Exception("Required anyQuery"))
    }

    data class Params(val apiKey: String, val map: HashMap<String, Any>) {
        companion object {
            const val PAGE_KEY = "page"
            const val GENRE_KEY = "with_genres"
        }
    }
}