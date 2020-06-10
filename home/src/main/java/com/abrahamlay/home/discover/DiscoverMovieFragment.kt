package com.abrahamlay.home.discover

import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.abrahamlay.base.subscriber.ResultState
import com.abrahamlay.detail.DetailFragment
import com.abrahamlay.domain.entities.MovieModel
import com.abrahamlay.home.MovieAdapter
import com.abrahamlay.home.MovieFragment
import com.abrahamlay.home.R
import kotlinx.android.synthetic.main.movie_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiscoverMovieFragment : MovieFragment<DiscoverViewModel>(), MovieAdapter.OnClickListener {
    private var viewStarted: Boolean = false
    private var genreId: Int? = 28
    override val viewModel by viewModel<DiscoverViewModel>()
    override fun onRefresh() {
        showLoading()
        genreId?.let { viewModel.refreshMovie(it) }
    }

    companion object {
        const val GENRE_ID = "GENRE_ID"

        fun newInstance(genreId: Int): DiscoverMovieFragment {
            val args = Bundle()

            args.putInt(GENRE_ID, genreId)
            val fragment = DiscoverMovieFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onInitViews() {
        super.onInitViews()
        genreId = arguments?.getInt(GENRE_ID)
        viewStarted = true
        getGenre()
    }

    override fun onInitObservers() {
        super.onInitObservers()

        adapter = MovieAdapter()

        (adapter as? MovieAdapter)?.onClickListener = this

        viewModel.movieData.observe(this, Observer {
            onMovieLoaded(it)
        })
    }

    private fun onMovieLoaded(resultState: ResultState<List<MovieModel>>) {
        when (resultState) {
            is ResultState.Success -> {
                hideLoading()
                (adapter as? MovieAdapter)?.data = resultState.data
                rvList.adapter = adapter
                rvList.layoutManager = getLayoutManager()
            }
            is ResultState.Error -> {
                hideLoading()
                Toast.makeText(context, resultState.throwable.message, Toast.LENGTH_SHORT).show()
            }
            is ResultState.Loading -> {
                showLoading()
            }
        }
    }

    override fun onItemClicked(data: Any) {
        Toast.makeText(context, (data as MovieModel).title, Toast.LENGTH_SHORT).show()
        val bundle = bundleOf(Pair(DetailFragment.PARAM_DETAIL_MOVIE, (data as MovieModel)))
        findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && viewStarted) {
            getGenre()
            viewStarted = false
        }
    }

    private fun getGenre() {
        genreId?.let {
            showLoading()
            viewModel.refreshMovie(it)
        }
    }
}
