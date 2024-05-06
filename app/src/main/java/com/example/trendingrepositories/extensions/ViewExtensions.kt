package com.example.trendingrepositories.extensions

import androidx.appcompat.widget.SearchView


inline fun SearchView.onQueryTextChanges(crossinline onTextChanged: (String?) -> Unit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?) = true

        override fun onQueryTextChange(query: String?): Boolean {
            onTextChanged(query)
            return true
        }
    })
}