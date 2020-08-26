package com.abrahamlay.data.mapper

import com.abrahamlay.data.dtos.ResultVideo
import com.abrahamlay.data.dtos.VideoDto
import com.abrahamlay.domain.entities.VideoModel

/**
 * Created by Abraham Lay on 25/08/20.
 */

fun VideoDto.map(): List<VideoModel> {
    return this.results?.map {
        it.map()
    } ?: arrayListOf()
}

fun ResultVideo.map(): VideoModel {
    return VideoModel(
        this.id,
        this.iso31661,
        this.iso6391,
        this.key,
        this.name,
        this.site,
        this.size,
        this.type
    )
}