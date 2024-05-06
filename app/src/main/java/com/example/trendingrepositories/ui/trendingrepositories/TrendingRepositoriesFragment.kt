package com.example.trendingrepositories.ui.trendingrepositories

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentTrendingRepositoriesBinding
import com.example.trendingrepositories.ui.home.HomeViewModel
import com.example.trendingrepositories.ui.trendingrepositories.adapters.TrendingRepositoryLoaderStateAdapter
import com.example.trendingrepositories.ui.trendingrepositories.adapters.TrendingRepositoryAdapter
import com.example.trendingrepositories.ui.trendingrepositories.models.TrendingRepositoryItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TrendingRepositoriesFragment : Fragment() {

    private val viewModel: TrendingRepositoriesViewModel by viewModels()
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private lateinit var binding: FragmentTrendingRepositoriesBinding
    private lateinit var adapter: TrendingRepositoryAdapter
    private lateinit var loaderStateAdapter: TrendingRepositoryLoaderStateAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentTrendingRepositoriesBinding.inflate(inflater, container, false)
            .also {
                binding = it
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModel()
    }

    private fun initViews() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@TrendingRepositoriesFragment.viewModel
        }
        setupTrendingRepositoryList()
        onTrendingRepositoriesSwipedToRefresh()
        handleTrendingRepositoriesState()
    }

    private fun initViewModel() {
        with(homeViewModel) {
            setShowMenuItems(
                showSearchView = true,
                showFilterView = true
            )

            dateTypeLiveData.observe(viewLifecycleOwner) { dateType ->
                viewModel.setDateType(dateType)
                viewModel.loadTrendingRepositories()
            }

            searchQueryLiveData.observe(viewLifecycleOwner) { query ->
                viewModel.search(query)
            }

            favouriteRepositoriesItemChangedEvent.observe(viewLifecycleOwner) {
                viewModel.onTrendingRepositoryDataChanged()
            }
        }

        viewModel.trendingRepositoriesItemsLiveData.observe(viewLifecycleOwner) { items ->
            lifecycleScope.launch {
                adapter.submitData(items)
            }
        }
    }

    private fun setupTrendingRepositoryList() {
        loaderStateAdapter = TrendingRepositoryLoaderStateAdapter {
            adapter.retry()
        }
        adapter = TrendingRepositoryAdapter(
            onItemClicked = { item ->
                onTrendingRepositoryItemClicked(item)
            },
            onAddItemToFavouriteClicked = { item ->
                onFavouriteClicked(item)
            })

        with(binding.trendingRepositoriesRecyclerview) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter =
                this@TrendingRepositoriesFragment.adapter.withLoadStateFooter(loaderStateAdapter)
        }
    }

    private fun onFavouriteClicked(item: TrendingRepositoryItem) {
        if (item.isSelected) {
            viewModel.deletedRepositoryFromFavourite(item)
        } else {
            viewModel.addRepositoryToFavourite(item)
        }
    }

    private fun onTrendingRepositoryItemClicked(item: TrendingRepositoryItem) {
        val bundle = bundleOf(EXTRA_TRENDING_REPOSITORY_ITEM to item)
        findNavController().navigate(
            R.id.action_trendingRepositories_to_trendingRepositoryDetails,
            bundle
        )
    }

    private fun onTrendingRepositoriesSwipedToRefresh() {
        with(binding.swipeToRefreshLayout) {
            setOnRefreshListener {
                isRefreshing = false
                viewModel.loadTrendingRepositories()
            }
        }
    }

    private fun handleTrendingRepositoriesState() {
        with(adapter) {
            addLoadStateListener { loadState ->
                viewModel.handlePassengersListState(loadState, itemCount)
            }

            binding.errorLayout.retryButton.setOnClickListener {
                retry()
            }
        }
    }

    companion object {
        private const val EXTRA_TRENDING_REPOSITORY_ITEM = "trendingRepositoryItem"
    }
}