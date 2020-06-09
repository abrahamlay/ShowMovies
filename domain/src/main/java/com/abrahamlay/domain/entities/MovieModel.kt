package com.abrahamlay.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Abraham Lay on 2020-06-09.
 */
@Parcelize
data class MovieModel(
    var voteCount: Int,
    var id: Int,
    var video: Boolean,
    var voteAverage: Double,
    var title: String?,
    var popularity: Double,
    var posterPath: String?,
    var originalLanguage: String?,
    var originalTitle: String?,
    var genreIds: List<Int>,
    var backdropPath: String?,
    var adult: Boolean,
    var overview: String?,
    var releaseDate: String?
) : Parcelable