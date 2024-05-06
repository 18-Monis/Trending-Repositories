package com.example.trendingrepositories.ui.trendingrepositories.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.R
import com.example.trendingrepositories.ui.trendingrepositories.models.TrendingRepositoryItem

class TrendingRepositoryAdapter(
    private val onItemClicked: (TrendingRepositoryItem) -> Unit,
    private val onAddItemToFavouriteClicked: (TrendingRepositoryItem) -> Unit
) : PagingDataAdapter<TrendingRepositoryItem, TrendingRepositoryViewHolder>(
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
    ): TrendingRepositoryViewHolder =
        TrendingRepositoryViewHolder(
            binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_trending_repository,
                parent,
                false,
            ), onItemClicked = { position ->
                getItem(position)?.let { onItemClicked.invoke(it) }
            },
            onAddItemToFavouriteClicked = { position ->
                getItem(position)?.let { onAddItemToFavouriteClicked.invoke(it) }
            }
        )

    override fun onBindViewHolder(holder: TrendingRepositoryViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}