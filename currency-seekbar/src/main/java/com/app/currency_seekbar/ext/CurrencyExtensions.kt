package com.app.currency_seekbar.ext

import android.os.Build
import android.view.View
import android.widget.SeekBar
import kotlin.math.roundToInt

fun View.visibleFromBoolean(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.isShow() = this.visibility == View.VISIBLE

fun SeekBar.splitTrack(splitTrack: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        this.splitTrack = splitTrack
    }
}

fun SeekBar.isMinProgress(): Boolean {
    return this.progress <= 0
}

fun SeekBar.isMaxProgress(): Boolean {
    return this.progress >= max
}

fun Double.toInt(round: Boolean): Int = if (round) this.roundToInt() else this.toInt()