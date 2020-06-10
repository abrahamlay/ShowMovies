package com.abrahamlay.home.discover

import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.abrahamlay.detail.DetailFragment
import com.abrahamlay.domain.entities.MovieModel
import com.abrahamlay.home.MovieAdapter
import com.abrahamlay.home.MovieFragment
import com.abrahamlay.home.R
import kotlinx.android.synthetic.main.movie_fragment.*
import kotlinx.android.synthetic.main.view_error.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiscoverMovieFragment : MovieFragment<DiscoverViewModel>(), MovieAdapter.OnClickListener {
    private var genreId: Int? = 28
    override val viewModel by viewModel<DiscoverViewModel>()
    override fun onRefresh() {
        getGenre()
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
    }

    override fun onInitObservers() {
        super.onInitObservers()
        getGenre()

        viewModel.productLiveData.observe(this, Observer { pagedList ->
            hideLoading()
            (adapter as MovieAdapter).submitList(pagedList)
        })

        viewModel.networkState.observe(this, Observer { networkState ->
            (adapter as MovieAdapter).setNetworkState(networkState)
        })
        viewModel.errorLiveData.observe(this, Observer { throwable ->
            showError(throwable)
        })

        btnRetry.setOnClickListener {
            getGenre()
        }
    }

    private fun initAdapter() {
        adapter = MovieAdapter()

        (adapter as? MovieAdapter)?.onClickListener = this

        rvList.adapter = adapter
        rvList.layoutManager = getLayoutManager()
    }

    override fun onItemClicked(data: MovieModel?) {
        data?.let {
            Toast.makeText(context, data.title, Toast.LENGTH_SHORT).show()
            val bundle = bundleOf(Pair(DetailFragment.PARAM_DETAIL_MOVIE, data))
            findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }
    }

    private fun getGenre() {
        genreId?.let {
            hideError()
            showLoading()
            viewModel.refreshMovie(it)
            initAdapter()
        }
    }
}
