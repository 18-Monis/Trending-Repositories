package com.example.trendingrepositories.ui.trendingrepositories.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ListItemTrendingRepositoryBinding
import com.example.trendingrepositories.extensions.toSpannedText
import com.example.trendingrepositories.ui.trendingrepositories.models.TrendingRepositoryItem


class TrendingRepositoryViewHolder(
    private val binding: ListItemTrendingRepositoryBinding,
    private val onItemClicked: (Int) -> Unit,
    private val onAddItemToFavouriteClicked: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private val context: Context
        get() = binding.root.context

    init {
        binding.root.setOnClickListener {
            if (absoluteAdapterPosition != RecyclerView.NO_POSITION) {
                onItemClicked.invoke(absoluteAdapterPosition)
            }
        }

        binding.addToFavourite.setOnClickListener {
            if (absoluteAdapterPosition != RecyclerView.NO_POSITION) {
                onAddItemToFavouriteClicked.invoke(absoluteAdapterPosition)
            }
        }
    }

    fun bind(item: TrendingRepositoryItem) {
        with(binding) {
            binding.item = item
            authorTextview.text = context.getString(
                R.string.created_by,
                item.author
            ).toSpannedText()
        }
    }
}