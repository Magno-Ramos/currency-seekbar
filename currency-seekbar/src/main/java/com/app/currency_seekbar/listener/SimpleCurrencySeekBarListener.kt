package com.app.currency_seekbar.listener

import android.widget.SeekBar

open class SimpleCurrencySeekBarListener : CurrencySeekBarListener {
    override fun onProgressChanged(
        seekBar: SeekBar?,
        progress: Double,
        formattedValue: String,
        fromUser: Boolean
    ) {

    }

    override fun onStartTrackingTouch(seekBar: SeekBar?, progress: Double, formattedValue: String) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?, progress: Double, formattedValue: String) {

    }
}