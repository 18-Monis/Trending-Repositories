package com.example.trendingrepositories.ui.favouriterepositories.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.myapplication.R
import com.example.trendingrepositories.ui.trendingrepositories.models.TrendingRepositoryItem

class FavouriteTrendingRepositoryAdapter(
    private val onItemClicked: (TrendingRepositoryItem) -> Unit,
    private val onRemoveItemClicked: (TrendingRepositoryItem) -> Unit,
) : ListAdapter<TrendingRepositoryItem, FavouriteTrendingRepositoryViewHolder>(
    object : DiffUtil.ItemCallback<TrendingRepositoryItem>() {
        override fun areItemsTheSame(
            oldItem: TrendingRepositoryItem,
            newItem: TrendingRepositoryItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TrendingRepositoryItem,
            newItem: TrendingRepositoryItem
        ): Boolean {
            return oldItem == newItem
        }
    }
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavouriteTrendingRepositoryViewHolder =
        FavouriteTrendingRepositoryViewHolder(
            binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_trending_repository,
                parent,
                false,
            ),
            onItemClicked = { position ->
                getItem(position)?.let {
                    onItemClicked.invoke(it)
                }
            },
            onRemoveItemClicked = { position ->
                getItem(position)?.let { onRemoveItemClicked.invoke(it) }
            }
        )

    override fun onBindViewHolder(
        holder: FavouriteTrendingRepositoryViewHolder,
        position: Int
    ) {
        getItem(position)?.let { holder.bind(it) }
    }
}