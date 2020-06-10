package com.abrahamlay.data.repoimplementations

import com.abrahamlay.data.apis.MovieAPI
import com.abrahamlay.domain.entities.*
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

    override fun getReviews(
        apiKey: String,
        movieId: Int,
        map: HashMap<String, Any>
    ): Flowable<List<ReviewModel>> = api.getReviews(movieId, apiKey, map).map { reviews ->
        reviews.results?.map { resultReview ->
            ReviewModel(
                resultReview.author,
                resultReview.content,
                resultReview.id,
                resultReview.url
            )
        }
    }

    override fun getVideo(apiKey: String, movieId: Int): Flowable<List<VideoModel>> =
        api.getVideo(movieId, apiKey).map { videos ->
            videos.results?.map { video ->
                VideoModel(
                    video.id,
                    video.iso31661,
                    video.iso6391,
                    video.key,
                    video.name,
                    video.site,
                    video.size,
                    video.type
                )
            }
        }


    override fun getMovieDetails(apiKey: String, movieId: Int): Flowable<DetailMovieModel> =
        api.getMovieDetails(movieId, apiKey).map {
            DetailMovieModel(
                it.adult,
                it.backdropPath,
                it.belongsToCollection,
                it.budget,
                it.genres?.map { genre -> DetailMovieModel.Genre(genre?.id, genre?.name) },
                it.homepage,
                it.id,
                it.imdbId,
                it.originalLanguage,
                it.originalTitle,
                it.overview,
                it.popularity,
                it.posterPath,
                it.productionCompanies?.map { company ->
                    DetailMovieModel.ProductionCompany(
                        company?.id,
                        company?.logoPath,
                        company?.name,
                        company?.originCountry
                    )
                },
                it.productionCountries?.map { country ->
                    DetailMovieModel.ProductionCountry(
                        country?.iso31661,
                        country?.name
                    )
                },
                it.releaseDate,
                it.revenue,
                it.runtime,
                it.spokenLanguages?.map { language ->
                    DetailMovieModel.SpokenLanguage(
                        language?.iso6391,
                        language?.name
                    )
                },
                it.status,
                it.tagline,
                it.originalTitle,
                it.video,
                it.voteAverage,
                it.voteCount
            )
        }
}