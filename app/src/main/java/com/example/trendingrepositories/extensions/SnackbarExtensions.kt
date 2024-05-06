package com.example.trendingrepositories.extensions

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

import android.content.Context
import android.view.Gravity
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

fun AppCompatActivity.showSnackBar(
    message: String,
    @ColorRes textColor: Int = R.color.white,
    @ColorRes backgroundColor: Int = R.color.colorPrimary,
    duration: Int = Snackbar.LENGTH_LONG,
    gravity: Int = Gravity.START,
    layoutDirection: Int = View.LAYOUT_DIRECTION_LTR,
    @StringRes actionText: Int? = null,
    onAction: (() -> Unit)? = null,
    @ColorRes actionTextColor: Int = R.color.white,
) {
    val root = findViewById<View>(android.R.id.content).rootView
    this.let {
        Snackbar.make(root, message, Snackbar.LENGTH_SHORT)
            .withColor(
                applicationContext,
                textColor,
                backgroundColor
            )
            .withGravity(gravity)
            .withLayoutDirection(layoutDirection)
            .withAction(applicationContext, actionText, actionTextColor, onAction)
    }.also {
        it.show()
    }
}

fun Snackbar.withColor(
    context: Context,
    @ColorRes textColor: Int = R.color.white,
    @ColorRes backgroundColor: Int = R.color.colorPrimary
): Snackbar {
    setBackgroundTint(ContextCompat.getColor(context, backgroundColor))
    setTextColor(ContextCompat.getColor(context, textColor))
    return this
}

fun Snackbar.withGravity(gravity: Int = Gravity.TOP): Snackbar {
    view.apply {
        val params = layoutParams as FrameLayout.LayoutParams
        params.gravity = gravity
        layoutParams = params
    }
    return this
}

fun Snackbar.withLayoutDirection(direction: Int = View.LAYOUT_DIRECTION_RTL): Snackbar {
    view.apply {
        layoutDirection = direction
    }
    return this
}


fun Snackbar.withAction(
    context: Context,
    @StringRes actionText: Int? = null,
    @ColorRes actionTextColor: Int = R.color.white,
    onAction: (() -> Unit)? = null
): Snackbar {
    if (actionText != null) {
        setActionTextColor(ContextCompat.getColor(context, actionTextColor))
        setAction(actionText) {
            onAction?.invoke()
        }
    }

    return this
}
