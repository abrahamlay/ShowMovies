package com.abrahamlay.base.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment

/**
 * Created by Abraham Lay on 2020-06-09.
 */

@SuppressLint("Registered")
abstract class BaseActivity<VM : ViewModel> : AppCompatActivity() {

    abstract val resourceLayout: Int?
    abstract val viewModel: VM

    protected val navController by lazy {
        onCreateNavController()?.let(NavHostFragment::findNavController)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resourceLayout?.let(this::setContentView)
        onInitViews()
        onInitObservers()
    }

    override fun onSupportNavigateUp() = navController?.navigateUp() ?: super.onSupportNavigateUp()

    protected open fun onCreateNavController(): NavHostFragment? = null

    protected open fun onInitViews() = Unit

    protected open fun onInitObservers() = Unit


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}