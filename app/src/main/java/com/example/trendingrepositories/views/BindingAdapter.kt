package com.example.trendingrepositories.views

import android.graphics.drawable.Drawable
import android.view.ContentInfo
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

@BindingAdapter("visibleGone")
fun setVisibility(view: View, value: Boolean) {
    view.visibility = if (value) View.VISIBLE else View.GONE
}

@BindingAdapter("image", "placeholder")
fun setImage(image: ImageView, url: String?, placeHolder: Drawable) {
    if (!url.isNullOrEmpty()) {
        Glide.with(image.context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .placeholder(placeHolder)
            .into(image)
    } else {
        image.setImageDrawable(placeHolder)
    }
}