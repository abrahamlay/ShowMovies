package com.abrahamlay.domain.interactors

import com.abrahamlay.domain.CoroutineUseCase
import com.abrahamlay.domain.entities.VideoModel
import com.abrahamlay.domain.repositories.MovieRepository
import javax.inject.Inject

/**
 * Created by Abraham Lay on 10/06/20.
 */

class GetVideos @Inject constructor(
    private val repository: MovieRepository
) : CoroutineUseCase<List<VideoModel>, GetVideos.Params>() {
    override suspend fun build(params: Params?): List<VideoModel> {
        return repository.getVideo(
            params?.apiKey ?: throw Exception("Required apiKey"),
            params.movieId ?: throw Exception("Required movieId")
        )
    }

    data class Params(val apiKey: String, val movieId: Int)
}