package com.abrahamlay.detail

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abrahamlay.base.constant.Constants.Companion.MOVIE_THUMBNAIL_BASE_URL_EXTRA_LARGE
import com.abrahamlay.base.presentation.BaseActivity
import com.abrahamlay.base.presentation.BaseFragment
import com.abrahamlay.base.subscriber.BaseViewModel
import com.abrahamlay.base.util.DateFormater
import com.abrahamlay.base.util.GlideHelper
import com.abrahamlay.domain.entities.MovieModel
import com.abrahamlay.home.R
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Created by Abraham Lay on 2020-06-09.
 */
class DetailFragment : BaseFragment<BaseViewModel>() {

    private var detailMovie: MovieModel? = null

    override val viewModel by viewModel<BaseViewModel>()

    companion object {
        const val PARAM_DETAIL_MOVIE = "detailMovie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            detailMovie = arguments?.getParcelable(PARAM_DETAIL_MOVIE)
        }
        setHasOptionsMenu(true)
//        movieHelper = MovieHelper(context)
//        favoriteState()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // create ContextThemeWrapper from the original Activity Context with the custom theme
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.AppTheme_NoActionBar)

        // clone the inflater using the ContextThemeWrapper
        val localInflater = inflater.cloneInContext(contextThemeWrapper)

        // Inflate the layout for this fragment
        return localInflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        if (detailMovie != null) {
            detail_overview.text = detailMovie?.overview
        }
    }

    private fun initToolbar() {
        val url = String.format(MOVIE_THUMBNAIL_BASE_URL_EXTRA_LARGE, detailMovie?.posterPath)
        GlideHelper.showImage(url, backdropMovie, context!!)

        (activity as BaseActivity<*>).setSupportActionBar(toolbar)

        val supportActionBar = (activity as BaseActivity<*>).supportActionBar

        if (supportActionBar != null) {
            supportActionBar.setTitle(detailMovie?.title)
            val releaseDate = DateFormater.changeDateFormat(
                detailMovie?.releaseDate
            )
            supportActionBar.setSubtitle(releaseDate)
            supportActionBar.setDisplayHomeAsUpEnabled(true)
        }
    }
}