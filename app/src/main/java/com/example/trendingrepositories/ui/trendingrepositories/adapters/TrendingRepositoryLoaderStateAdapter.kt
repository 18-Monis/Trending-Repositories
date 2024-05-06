package com.example.trendingrepositories.ui.trendingrepositories.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.example.myapplication.R

class TrendingRepositoryLoaderStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<TrendingRepositoryLoaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): TrendingRepositoryLoaderViewHolder {
        return TrendingRepositoryLoaderViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.trending_repository_loader_state,
                parent,
                false,
            ),
            retry
        )
    }

    override fun onBindViewHolder(holder: TrendingRepositoryLoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}