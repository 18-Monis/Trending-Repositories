package com.example.trendingrepositories.ui.trendingrepositories.adapters

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.TrendingRepositoryLoaderStateBinding

class TrendingRepositoryLoaderViewHolder(
    private val binding: TrendingRepositoryLoaderStateBinding,
    retry: () -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.errorLayout.retryButton.setOnClickListener {
            retry()
        }
    }

    fun bind(loadState: LoadState) {
        with(binding) {
            progressbar.isVisible = loadState is LoadState.Loading
            binding.errorLayout.showError = loadState !is LoadState.Loading
        }
    }
}