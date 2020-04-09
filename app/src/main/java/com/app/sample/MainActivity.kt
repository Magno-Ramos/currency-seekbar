package com.app.sample

import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.app.currency_seekbar.CurrencySeekBar
import com.app.currency_seekbar.listener.SimpleCurrencySeekBarListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupDefault()
        setupCustom()
        setupHighValues()
        setupVeryHighValues()
        setupWithOutStep()
    }

    private fun setupDefault() {
        setup(cs_default, text_view_1)
    }

    private fun setupCustom() {
        setup(cs_custom, text_view_custom)
    }

    private fun setupHighValues() {
        setup(cs_high, text_view_high)
        cs_high?.setLocale(Locale.US)
    }

    private fun setupVeryHighValues() {
        setup(cs_very_high, text_view_very_high)
    }

    private fun setupWithOutStep() {
        setup(cs_without_step, text_view_without_step)
        cs_without_step?.setEnableStep(false)
    }

    private fun setup(currencySeekBar: CurrencySeekBar?, textView: TextView?) {
        currencySeekBar?.setOnSeekBarChangeListener(object : SimpleCurrencySeekBarListener() {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Double,
                formattedValue: String,
                fromUser: Boolean
            ) {
                super.onProgressChanged(seekBar, progress, formattedValue, fromUser)
                textView?.text = formattedValue
            }
        })
    }
}
