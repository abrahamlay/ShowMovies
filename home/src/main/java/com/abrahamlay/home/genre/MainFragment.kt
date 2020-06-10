package com.abrahamlay.home.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.abrahamlay.base.presentation.BaseActivity
import com.abrahamlay.base.presentation.TabFragment
import com.abrahamlay.base.subscriber.ResultState
import com.abrahamlay.domain.entities.GenreModel
import com.abrahamlay.home.R
import com.abrahamlay.home.databinding.FragmentMainBinding
import com.abrahamlay.home.discover.DiscoverMovieFragment
import kotlinx.android.synthetic.main.fragment_detail.toolbar
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.view_error.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Abraham Lay on 2020-06-09.
 */
class MainFragment : TabFragment<GenreViewModel>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        tabLayout = binding.root.findViewById(com.abrahamlay.base.R.id.tab)
        pager = binding.root.findViewById(com.abrahamlay.base.R.id.pager)
        return binding.root
    }

    override fun onInitViews() {
        super.onInitViews()
        (activity as BaseActivity<*>).setSupportActionBar(toolbar)

        val supportActionBar = (activity as BaseActivity<*>).supportActionBar

        supportActionBar?.title = getString(R.string.app_name)
    }

    override fun onInitObservers() {
        super.onInitObservers()
        showLoading()
        viewModel.genreData.observe(this, Observer {
            onResultLoaded(it)
        })

        btnRetry.setOnClickListener {
            hideError()
            showLoading()
            viewModel.refreshGenre()
        }
    }

    private fun onResultLoaded(resultState: ResultState<List<GenreModel>>?) {
        hideLoading()
        when (resultState) {
            is ResultState.Success -> {
                onGetGenres(resultState.data)
            }
            is ResultState.Error -> {
                showError(resultState.throwable)
            }
        }

    }

    private fun onGetGenres(genres: List<GenreModel>) {
        tabLayout?.visibility = View.VISIBLE
        for (genre in genres) {
            titles.add(genre.name)
            fragments.add(DiscoverMovieFragment.newInstance(genre.id))
        }
        initTabAndPager()
    }

    override val viewModel by viewModel<GenreViewModel>()

    private fun showError(throwable: Throwable) {
        errorView.visibility = View.VISIBLE
        tvErrorMessage.text = throwable.localizedMessage
    }

    private fun hideError() {
        errorView.visibility = View.GONE
    }

    private fun showLoading() {
        progressBarView.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBarView.visibility = View.GONE
    }

}