package com.abrahamlay.data.repoimplementations

import com.abrahamlay.data.apis.MovieAPI
import com.abrahamlay.domain.entities.GenreModel
import com.abrahamlay.domain.entities.MovieModel
import com.abrahamlay.domain.repositories.MovieRepository
import io.reactivex.Flowable

/**
 * Created by Abraham Lay on 2020-06-09.
 */


class MovieRepositoryImpl constructor(private val api: MovieAPI) : MovieRepository {
    override fun getGenres(apiKey: String): Flowable<List<GenreModel>> = api.getGenres(apiKey).map {
        it.genres?.map { genre ->
            GenreModel(
                genre.id ?: throw NullPointerException("Expression 'genre.id' must not be null"),
                genre.name ?: throw NullPointerException("Expression 'genre.name' must not be null")
            )
        }
    }

    override fun getDiscoverMovies(
        apiKey: String,
        map: HashMap<String, Any>
    ): Flowable<List<MovieModel>> = api.getDiscoverMoviesByGenre(apiKey, map).map {
        it.results.map { movie ->
            MovieModel(
                movie.voteCount,
                movie.id,
                movie.video,
                movie.voteAverage,
                movie.originalTitle,
                movie.popularity,
                movie.posterPath,
                movie.originalLanguage,
                movie.originalTitle,
                movie.genreIds,
                movie.backdropPath,
                movie.adult,
                movie.overview,
                movie.releaseDate
            )
        }
    }
}