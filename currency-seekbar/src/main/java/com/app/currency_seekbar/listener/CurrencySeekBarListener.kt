package com.app.currency_seekbar.listener

import android.widget.SeekBar

interface CurrencySeekBarListener {
    fun onProgressChanged(
        seekBar: SeekBar?,
        progress: Double,
        formattedValue: String,
        fromUser: Boolean
    ) {

    }

    fun onStartTrackingTouch(seekBar: SeekBar?, progress: Double, formattedValue: String) {

    }

    fun onStopTrackingTouch(seekBar: SeekBar?, progress: Double, formattedValue: String) {

    }
}