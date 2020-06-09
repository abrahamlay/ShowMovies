package com.abrahamlay.home

import com.abrahamlay.domain.entities.MovieModel

/**
 * Created by Abraham Lay on 2020-06-09.
 */

interface ViewContract{
    fun onMovieLoaded(list: List<MovieModel>)
}