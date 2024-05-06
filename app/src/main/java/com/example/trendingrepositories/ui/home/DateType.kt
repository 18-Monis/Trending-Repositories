package com.example.trendingrepositories.ui.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class DateType : Parcelable {
    LAST_DAY, LAST_WEEK, LAST_MONTH
}