package com.bismillah.quran.extentions

import android.content.res.Resources
import android.view.View

fun View.visibility(visibility: Boolean): View {
    if (visibility) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
    return this
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Float.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Int.dpToFloat: Float
    get() = (this/Resources.getSystem().displayMetrics.density)
