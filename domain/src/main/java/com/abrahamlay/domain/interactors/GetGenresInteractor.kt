package com.abrahamlay.domain.interactors

import com.abrahamlay.domain.CoroutineUseCase
import com.abrahamlay.domain.entities.GenreModel
import com.abrahamlay.domain.repositories.MovieRepository
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by Abraham Lay on 09/06/20.
 */

class GetGenresInteractor @Inject constructor(
    private val repository: MovieRepository
) : CoroutineUseCase<List<GenreModel>, GetGenresInteractor.Params>() {
    override suspend fun build(params: Params?): List<GenreModel> =
        repository.getGenres(params?.apiKey ?: throw Exception("Required apiKey"))

    data class Params(val apiKey: String)
}