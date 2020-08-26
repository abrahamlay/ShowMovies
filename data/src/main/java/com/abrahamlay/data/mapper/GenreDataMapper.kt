package com.abrahamlay.data.mapper

import com.abrahamlay.data.dtos.Genre
import com.abrahamlay.data.dtos.GenresDto
import com.abrahamlay.domain.entities.GenreModel

/**
 * Created by Abraham Lay on 25/08/20.
 */

fun GenresDto.map(): List<GenreModel> {
    return this.genres?.map {
        it.map()
    } ?: arrayListOf()
}

fun Genre.map(): GenreModel {
    return GenreModel(
        this.id ?: throw NullPointerException("Expression 'genre.id' must not be null"),
        this.name ?: throw NullPointerException("Expression 'genre.name' must not be null")
    )
}