package com.abrahamlay.home.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.abrahamlay.base.presentation.TabFragment
import com.abrahamlay.base.subscriber.ResultState
import com.abrahamlay.domain.entities.GenreModel
import com.abrahamlay.home.DiscoverMovieFragment
import com.abrahamlay.home.databinding.FragmentMainBinding
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

    override fun onInitObservers() {
        super.onInitObservers()
        viewModel.genreData.observe(this, Observer {
            onResultLoaded(it)
        })
    }

    private fun onResultLoaded(resultState: ResultState<List<GenreModel>>?) {
        when (resultState) {
            is ResultState.Success -> {
                onGetGenres(resultState.data)
            }
            is ResultState.Error -> {
                Toast.makeText(context, resultState.throwable.message, Toast.LENGTH_SHORT).show()
            }
            is ResultState.Loading -> {
            }
        }

    }

    private fun onGetGenres(genres: List<GenreModel>) {
        for (genre in genres) {
            titles.add(genre.name)
            fragments.add(DiscoverMovieFragment.newInstance(genre.id))
        }
        initTabAndPager()
    }

    override val viewModel by viewModel<GenreViewModel>()

}