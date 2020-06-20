package com.abrahamlay.home

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.abrahamlay.domain.entities.MovieModel
import com.abrahamlay.home.databinding.ViewMovieCardBinding


/**
 * Created by Abraham Lay on 19/06/20.
 */
class MovieCard @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    val binding = ViewMovieCardBinding.inflate(LayoutInflater.from(context), this, true)

    fun setMovieModel(obj: MovieModel?) {
        obj?.let {
            binding.movieModel = obj
        }
    }
}