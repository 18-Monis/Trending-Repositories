package com.example.trendingrepositories.ui.trendingrepositorydetails

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentTrendingRepositoryDetailsBinding
import com.example.trendingrepositories.extensions.toSpannedText
import com.example.trendingrepositories.ui.home.HomeViewModel

class TrendingRepositoryDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTrendingRepositoryDetailsBinding
    private val viewModel: TrendingRepositoryDetailsViewModel by viewModels()
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private val args: TrendingRepositoryDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentTrendingRepositoryDetailsBinding.inflate(inflater, container, false)
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
            item = tendingRepositoryItem
            createdAtTextview.text =
                getString(R.string.created_at, tendingRepositoryItem.createdAt).toSpannedText()
            authorTextview.text =
                getString(R.string.created_by, tendingRepositoryItem.author).toSpannedText()
        }
    }

    private fun initViewModel() {
        homeViewModel.setShowMenuItems(
            showSearchView = false,
            showFilterView = false
        )
    }

    private val tendingRepositoryItem by lazy { args.trendingRepositoryItem }
}