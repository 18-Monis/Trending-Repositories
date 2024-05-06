package com.example.trendingrepositories.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityHomeBinding
import com.example.trendingrepositories.common.network.Status
import com.example.trendingrepositories.common.observeEvent
import com.example.trendingrepositories.extensions.onBackButtonPressed
import com.example.trendingrepositories.extensions.onQueryTextChanges
import com.example.trendingrepositories.extensions.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private var filterDialog: TrendingRepositoriesFilterBottomSheetDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        initViews()
        initViewModel()
    }

    private fun initViews() {
        binding.lifecycleOwner = this@HomeActivity
        setupNavigation()
        onBackButtonPressed()
        addMenuProvider(menuProvider)

    }

    private fun initViewModel() {
        viewModel.connectivityStatusEvent.observeEvent(this) { status ->
            var message: String? = null
            var color: Int = R.color.colorPrimary

            when (status) {
                Status.UNAVAILABLE,
                Status.LOST -> {
                    message = getString(R.string.no_internet_connection_message)
                    color = R.color.red
                }

                Status.LOSING -> {
                    message = getString(R.string.losing_internet_connection_message)
                    color = R.color.orange
                }

                else -> Unit
            }

            message?.let {
                showSnackBar(
                    message = it,
                    backgroundColor = color
                )
            }
        }

        viewModel.showSearchViewLiveData.observe(this) {
            invalidateMenu()
        }

        viewModel.showFilterLiveData.observe(this) {
            invalidateMenu()
        }
    }

    private fun setupNavigation() {
        setSupportActionBar(binding.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val config = AppBarConfiguration(navController.graph)
        with(binding) {
            toolbar.setupWithNavController(navController, config)
            bottomNavigationView.setupWithNavController(navController)
        }
    }

    private val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(
                R.menu.trending_repositories_menu, menu
            )
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when (menuItem.itemId) {
                R.id.filter_item -> {
                    TrendingRepositoriesFilterBottomSheetDialog.show(
                        supportFragmentManager,
                        viewModel.dateType
                    ).apply {
                        onDateTypeChecked = { dateType ->
                            viewModel.setDateType(dateType)
                        }
                    }
                    true
                }

                else -> true
            }
        }

        override fun onPrepareMenu(menu: Menu) {
            super.onPrepareMenu(menu)
            val filterItem = menu.findItem(R.id.filter_item)
            val searchItem = menu.findItem(R.id.search_item)

            searchItem.isVisible = viewModel.showSearchView
            filterItem.isVisible = viewModel.showFilterView

            (searchItem.actionView as SearchView).apply {
                queryHint = getString(R.string.search)
                onQueryTextChanges { query ->
                    viewModel.setSearchQuery(query.orEmpty())
                }
            }
        }
    }
}