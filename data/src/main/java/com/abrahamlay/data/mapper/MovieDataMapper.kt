package com.abrahamlay.data.mapper

import com.abrahamlay.data.dtos.MovieDto
import com.abrahamlay.domain.entities.MovieModel

/**
 * Created by Abraham Lay on 25/08/20.
 */

fun MovieDto.map(): List<MovieModel> {
    return this.results.map {
        it.map()
    }
}

fun MovieDto.Movie.map(): MovieModel {
    return MovieModel(
        this.voteCount,
        this.id,
        this.video,
        this.voteAverage,
        this.originalTitle,
        this.popularity,
        this.posterPath,
        this.originalLanguage,
        this.originalTitle,
        this.genreIds,
        this.backdropPath,
        this.adult,
        this.overview,
        this.releaseDate
    )
}