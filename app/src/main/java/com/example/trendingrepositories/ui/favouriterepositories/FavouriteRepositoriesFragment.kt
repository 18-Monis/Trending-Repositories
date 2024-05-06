package com.example.trendingrepositories.ui.favouriterepositories

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFavouriteRepositoriesBinding
import com.example.trendingrepositories.ui.favouriterepositories.adapters.FavouriteTrendingRepositoryAdapter
import com.example.trendingrepositories.ui.home.HomeViewModel
import com.example.trendingrepositories.ui.trendingrepositories.models.TrendingRepositoryItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteRepositoriesFragment : Fragment() {

    private val viewModel: FavouriteRepositoriesViewModel by viewModels()
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private lateinit var binding: FragmentFavouriteRepositoriesBinding
    private lateinit var adapter: FavouriteTrendingRepositoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentFavouriteRepositoriesBinding.inflate(inflater, container, false)
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
            viewModel = this@FavouriteRepositoriesFragment.viewModel
        }
        setupTrendingRepositoryList()
    }

    private fun initViewModel() {
        homeViewModel.setShowMenuItems(
            showSearchView = true ,
            showFilterView = false
        )
        homeViewModel.searchQueryLiveData.observe(viewLifecycleOwner) { query ->
            viewModel.search(query)
        }
        viewModel.favouriteRepositoriesItemsLiveData.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }
    }

    private fun setupTrendingRepositoryList() {
        adapter = FavouriteTrendingRepositoryAdapter(
            onItemClicked = { item ->
                onFavouriteRepositoryItemClicked(item)
            },
            onRemoveItemClicked = { item ->
                viewModel.deletedRepositoryFromFavourite(item)
                homeViewModel.notifyDataChanged()
            })

        with(binding.trendingRepositoriesRecyclerview) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FavouriteRepositoriesFragment.adapter
        }
    }

    private fun onFavouriteRepositoryItemClicked(item: TrendingRepositoryItem) {
        val bundle = bundleOf(EXTRA_TRENDING_REPOSITORY_ITEM to item)
        findNavController().navigate(
            R.id.action_favouriteRepositories_to_trendingRepositoryDetails,
            bundle
        )
    }

    companion object {
        private const val EXTRA_TRENDING_REPOSITORY_ITEM = "trendingRepositoryItem"
    }
}