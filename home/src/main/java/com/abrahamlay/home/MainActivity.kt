package com.abrahamlay.home

import android.view.MenuItem
import androidx.navigation.fragment.NavHostFragment
import com.abrahamlay.base.presentation.BaseActivity
import com.abrahamlay.base.subscriber.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Abraham Lay on 2020-06-09.
 */

class MainActivity : BaseActivity<BaseViewModel>() {
    private lateinit var navHostFragment: NavHostFragment
    override val viewModel by viewModel<BaseViewModel>()
    override val resourceLayout: Int?
        get() = R.layout.activity_main


    override fun onInitViews() {
        super.onInitViews()
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
    }

    override fun onBackPressed() {
        if (!navHostFragment.navController.popBackStack())
            finish()
        else
            navHostFragment.navController.navigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (!navHostFragment.navController.popBackStack())
                    finish()
                else
                    navHostFragment.navController.navigateUp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
