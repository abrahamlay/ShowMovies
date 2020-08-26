package com.abrahamlay.data.mapper

import com.abrahamlay.data.dtos.DetailMovieDto
import com.abrahamlay.domain.entities.DetailMovieModel

/**
 * Created by Abraham Lay on 24/08/20.
 */

fun DetailMovieDto.map(): DetailMovieModel {
    return DetailMovieModel(
        this.adult,
        this.backdropPath,
        this.belongsToCollection,
        this.budget,
        this.genres?.map { genre -> DetailMovieModel.Genre(genre?.id, genre?.name) },
        this.homepage,
        this.id,
        this.imdbId,
        this.originalLanguage,
        this.originalTitle,
        this.overview,
        this.popularity,
        this.posterPath,
        this.productionCompanies?.map { company ->
            DetailMovieModel.ProductionCompany(
                company?.id,
                company?.logoPath,
                company?.name,
                company?.originCountry
            )
        },
        this.productionCountries?.map { country ->
            DetailMovieModel.ProductionCountry(
                country?.iso31661,
                country?.name
            )
        },
        this.releaseDate,
        this.revenue,
        this.runtime,
        this.spokenLanguages?.map { language ->
            DetailMovieModel.SpokenLanguage(
                language?.iso6391,
                language?.name
            )
        },
        this.status,
        this.tagline,
        this.originalTitle,
        this.video,
        this.voteAverage,
        this.voteCount
    )
}