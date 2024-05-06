package com.example.trendingrepositories.extensions

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.Spanned

@SuppressLint("ObsoleteSdkInt")
fun String?.toSpannedText(): Spanned {
    // return an empty spannable if the string is null
    if (this == null) {
        return SpannableString("")
    }

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(this)
    }
}