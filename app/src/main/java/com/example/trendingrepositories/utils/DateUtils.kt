package com.example.trendingrepositories.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtils {

    const val SERVER_DATE_FORMAT = "MMM dd, yyyy hh:mm"
    private const val ISO_DAY_FORMAT = "yyyy-MM-dd"
    private const val ISO_SERVER_DATE = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    fun formatDate(
        date: Date,
        format: String = ISO_DAY_FORMAT,
        locale: Locale = Locale.ENGLISH
    ): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(date)
    }

    fun parseDate(
        date: String,
        format: String = ISO_SERVER_DATE,
        locale: Locale = Locale.ENGLISH
    ): Date {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.parse(date) as Date
    }

    fun getLastMonthDate(date:Date):Date{
        return Calendar.getInstance().apply {
            time = date
            add(Calendar.MONTH, -1)
        }.time
    }

    fun getLastWeekDate(date:Date):Date{
        return Calendar.getInstance().apply {
            time = date
            add(Calendar.DAY_OF_YEAR, -7);
        }.time
    }
}