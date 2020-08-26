package com.abrahamlay.base.navigation

import androidx.navigation.NavController

/**
 * Created by Abraham Lay on 16/06/20.
 */
interface CommonNavigation {
    fun navigateToDetail(findNavController: NavController, data: Any)
}