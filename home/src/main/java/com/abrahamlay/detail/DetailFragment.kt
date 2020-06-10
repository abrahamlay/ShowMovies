package com.abrahamlay.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.view.ContextThemeWrapper
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abrahamlay.base.constant.Constants
import com.abrahamlay.base.presentation.BaseActivity
import com.abrahamlay.base.presentation.BaseFragment
import com.abrahamlay.base.subscriber.BaseViewModel
import com.abrahamlay.base.subscriber.ResultState
import com.abrahamlay.base.util.DateFormater
import com.abrahamlay.base.util.GlideHelper
import com.abrahamlay.detail.reviews.ReviewAdapter
import com.abrahamlay.detail.reviews.ReviewViewModel
import com.abrahamlay.detail.video.VideoViewModel
import com.abrahamlay.domain.entities.MovieModel
import com.abrahamlay.domain.entities.VideoModel
import com.abrahamlay.home.R
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.movie_fragment.errorView
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_review.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Created by Abraham Lay on 2020-06-09.
 */
class DetailFragment : BaseFragment<BaseViewModel>() {

    private lateinit var adapter: ReviewAdapter
    private var detailMovie: MovieModel? = null

    override val viewModel by viewModel<ReviewViewModel>()

    private val viewModelVideo by viewModel<VideoViewModel>()

    companion object {
        const val PARAM_DETAIL_MOVIE = "detailMovie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            detailMovie = arguments?.getParcelable(PARAM_DETAIL_MOVIE)
        }
        setHasOptionsMenu(true)
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


    override fun onInitViews() {
        super.onInitViews()
        initToolbar()
        if (detailMovie != null) {
            detailOverview.text = detailMovie?.overview
            detailRating.text = detailMovie?.popularity.toString()
            val releaseDate = DateFormater.changeDateFormat(
                detailMovie?.releaseDate
            )
            detailDateRelease.text = releaseDate
            val url =
                String.format(Constants.MOVIE_THUMBNAIL_BASE_URL_MEDIUM, detailMovie?.backdropPath)
            GlideHelper.showImage(url, ivMovie, context!!)

            detailMovie?.id?.let {
                viewModelVideo.refreshVideo(it)
            }
        }
        viewModelVideo.videoData.observe(this, Observer {
            initVideo(it)
        })
    }

    override fun onInitObservers() {
        super.onInitObservers()
        getReviews()
        viewModel.errorLiveData.observe(this, Observer {
            showError(it)
        })
        btnRetry.setOnClickListener {
            getReviews()
        }
    }

    private fun initAdapter() {
        adapter = ReviewAdapter()

        viewModel.productLiveData.observe(this, Observer { pagedList ->
            hideLoading()
            adapter.submitList(pagedList)
        })

        viewModel.networkState.observe(this, Observer { networkState ->
            adapter.setNetworkState(networkState)
        })

        rvReviewsList.adapter = adapter
        rvReviewsList.layoutManager = getLayoutManager()
    }

    private fun initToolbar() {

        (activity as BaseActivity<*>).setSupportActionBar(toolbar)

        val supportActionBar = (activity as BaseActivity<*>).supportActionBar

        if (supportActionBar != null) {
            supportActionBar.title = detailMovie?.title
            supportActionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initVideo(state: ResultState<List<VideoModel>>) {
        if (state is ResultState.Success) {
            initWebView(state.data[0].key)
        }

    }

    private fun initWebView(key: String?) {
        //build your own src link with your video ID
        val videoStr =
            "<iframe style=\"margin: auto;\" width=\"370\" height=\"240\" src=\"https://www.youtube.com/embed/$key\" autoplay=\"1\" allowfullscreen/>"

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return false
            }
        }
        val ws: WebSettings = webView.settings
        ws.javaScriptEnabled = true
        webView.loadData(videoStr, "text/html", "utf-8")
    }

    private fun getLayoutManager(): RecyclerView.LayoutManager? {
        return LinearLayoutManager(context)
    }

    private fun showError(throwable: Throwable) {
        errorView.visibility = View.VISIBLE
        tvErrorMessage.text = throwable.localizedMessage
    }

    private fun hideError() {
        errorView.visibility = View.GONE
    }

    fun getReviews() {
        detailMovie?.id?.let {
            hideError()
            showLoading()
            viewModel.refreshReview(it)
            initAdapter()
        }
    }

    private fun showLoading() {
        progressBarView.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBarView.visibility = View.GONE
    }
}