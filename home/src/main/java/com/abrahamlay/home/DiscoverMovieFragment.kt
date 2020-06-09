package com.abrahamlay.home

import android.os.Bundle
import com.abrahamlay.base.presentation.BaseFragment
import com.abrahamlay.base.subscriber.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiscoverMovieFragment : BaseFragment<BaseViewModel>() {
    override val viewModel by viewModel<BaseViewModel>()

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
}
