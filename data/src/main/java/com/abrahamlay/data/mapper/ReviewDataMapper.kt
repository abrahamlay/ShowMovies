package com.abrahamlay.data.mapper

import com.abrahamlay.data.dtos.ResultReview
import com.abrahamlay.data.dtos.ReviewDto
import com.abrahamlay.domain.entities.ReviewModel

/**
 * Created by Abraham Lay on 25/08/20.
 */

fun ReviewDto.map(): List<ReviewModel> {
    return this.results?.map {
        it.map()
    } ?: arrayListOf()
}

fun ResultReview.map(): ReviewModel {
    return ReviewModel(
        this.author,
        this.content,
        this.id,
        this.url
    )
}