package com.example.trendingrepositories.ui.favouriterepositories.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ListItemTrendingRepositoryBinding
import com.example.trendingrepositories.extensions.toSpannedText
import com.example.trendingrepositories.ui.trendingrepositories.models.TrendingRepositoryItem


class FavouriteTrendingRepositoryViewHolder(
    private val binding: ListItemTrendingRepositoryBinding,
    private val onItemClicked: (Int) -> Unit,
    private val onRemoveItemClicked: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private val context: Context
        get() = binding.root.context

    init {
        binding.root.setOnClickListener {
            if (absoluteAdapterPosition != RecyclerView.NO_POSITION) {
                onItemClicked.invoke(absoluteAdapterPosition)
            }
        }

        binding.deleteImageView.setOnClickListener {
            if (absoluteAdapterPosition != RecyclerView.NO_POSITION) {
                onRemoveItemClicked.invoke(absoluteAdapterPosition)

            }
        }
    }

    fun bind(item: TrendingRepositoryItem) {
        with(binding) {
            this.item = item
            showDeleteAction = true
            authorTextview.text =
                context.getString(R.string.created_by, item.author).toSpannedText()
        }
    }
}