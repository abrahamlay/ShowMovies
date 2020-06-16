package com.abrahamlay.showmovies.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.abrahamlay.base.navigation.CommonNavigation
import com.abrahamlay.detail.DetailFragment
import com.abrahamlay.domain.entities.MovieModel
import com.abrahamlay.home.R

/**
 * Created by Abraham Lay on 16/06/20.
 */
class CommonNavigationImplementation : CommonNavigation {
    override fun navigateToDetail(findNavController: NavController, data: Any) {
        if (data is MovieModel) {
            val bundle = bundleOf(Pair(DetailFragment.PARAM_DETAIL_MOVIE, data))
            findNavController.navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }
    }
}