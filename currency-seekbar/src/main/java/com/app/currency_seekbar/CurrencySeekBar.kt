package com.app.currency_seekbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import com.app.currency_seekbar.ext.*
import com.app.currency_seekbar.listener.CurrencySeekBarListener
import com.app.currency_seekbar.listener.SimpleSeekBarListener
import java.text.NumberFormat
import java.util.*
import kotlin.math.min
import kotlin.math.roundToInt

class CurrencySeekBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var locale: Locale = Locale.getDefault()
    private var numberFormat: NumberFormat = NumberFormat.getCurrencyInstance(locale)

    private val mSeekBar: SeekBar
    private val minLabel: TextView
    private val maxLabel: TextView

    private var mProgress: Double = DEFAULT_PROGRESS
    private var mMin: Double = DEFAULT_MIN
    private var mMax: Double = DEFAULT_MAX

    private var enableStep: Boolean = DEFAULT_ENABLE_STEP
    private var enableRound: Boolean = DEFAULT_ENABLE_ROUND

    private var step: () -> Int = {
        val interval = (mMax - mMin)
        when {
            interval > 100000 -> 1000
            interval > 1000 -> 100
            interval > 500 -> 50
            interval > 200 -> 10
            interval > 50 -> 5
            else -> 1
        }
    }

    private var listener: CurrencySeekBarListener? = null

    init {
        orientation = VERTICAL
        layoutParams = ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

        LayoutInflater.from(context).inflate(R.layout.csb_layout, this, true)

        mSeekBar = findViewById(R.id.csb_seek_bar)
        minLabel = findViewById(R.id.text_view_1)
        maxLabel = findViewById(R.id.text_view_2)

        attrs?.let {
            val tar = context.obtainStyledAttributes(it, R.styleable.CurrencySeekBar, 0, 0)

            try {
                val max = tar.getString(R.styleable.CurrencySeekBar_cs_max)?.toDoubleOrNull()
                max?.let { v -> setMax(v) } ?: setMax(DEFAULT_MAX)
            } catch (e: Exception) {
                throw IllegalArgumentException("max value is invalid ")
            }

            try {
                val min = tar.getString(R.styleable.CurrencySeekBar_cs_min)?.toDoubleOrNull()
                min?.let { v -> setMin(v) } ?: setMin(DEFAULT_MIN)
            } catch (e: Exception) {
                throw IllegalArgumentException("min value is invalid ")
            }

            try {
                val progress =
                    tar.getString(R.styleable.CurrencySeekBar_cs_progress)?.toDoubleOrNull()
                progress?.let { v -> setProgress(v) } ?: setProgress(DEFAULT_PROGRESS)
            } catch (e: Exception) {
                throw IllegalArgumentException("progress value is invalid")
            }

            val showMinLabel = tar.getBoolean(R.styleable.CurrencySeekBar_cs_show_min_label, true)
            showMinLabel(showMinLabel)

            val showMaxLabel = tar.getBoolean(R.styleable.CurrencySeekBar_cs_show_max_label, true)
            showMaxLabel(showMaxLabel)

            val enableStep =
                tar.getBoolean(R.styleable.CurrencySeekBar_cs_enable_step, DEFAULT_ENABLE_STEP)
            setEnableStep(enableStep)

            tar.recycle()
            setupSeekBar()
        }

        refreshSeekProgress(false)
    }

    fun showMinLabel(show: Boolean) = this.minLabel.visibleFromBoolean(show)

    fun showMaxLabel(show: Boolean) = this.maxLabel.visibleFromBoolean(show)

    fun minLabelIsShow() = minLabel.isShow()

    fun maxLabelIsShow() = maxLabel.isShow()

    fun setMax(max: Number) {
        if (max.toDouble() <= mMin)
            throw IllegalArgumentException("the `Max` value must be greater than the min value")

        mMax = max.toDouble()
        refreshMaxTextView()
        refreshSeekProgress()
    }

    fun setMin(min: Number) {
        if (min.toDouble() >= mMax)
            throw IllegalArgumentException("The `Min value must be less than the max value")

        mMin = min.toDouble()
        refreshMinTextView()
        refreshSeekProgress()
    }

    fun setProgress(progress: Number) {
        mProgress = when {
            progress.toDouble() < mMin -> mMin
            progress.toDouble() > mMax -> mMax
            else -> {
                if (enableRound) progress.roundToDouble() else progress.toDouble()
            }
        }
        refreshSeekProgress()
    }

    fun getProgress(): Double = mProgress

    fun setEnableStep(enable: Boolean) {
        enableStep = enable
    }

    fun setEnableRound(enable: Boolean) {
        enableRound = enable
    }

    fun setOnSeekBarChangeListener(listener: CurrencySeekBarListener?) {
        this.listener = listener
        listener?.onProgressChanged(mSeekBar, mProgress, mProgress.formatCurrency(), false)
    }

    fun setLocale(locale: Locale) {
        numberFormat = NumberFormat.getCurrencyInstance(locale)
        refreshMinTextView()
        refreshMaxTextView()
    }

    private fun setupSeekBar() {
        mSeekBar.splitTrack(false)
        mSeekBar.setOnSeekBarChangeListener(object : SimpleSeekBarListener() {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                when {
                    mSeekBar.isMinProgress() -> mProgress = mMin
                    mSeekBar.isMaxProgress() -> mProgress = mMax
                    else -> {
                        if (fromUser)
                            mProgress = (progress + mMin.roundToInt()).toDouble()

                        refreshSeekProgress(enableStep)
                    }
                }

                listener?.onProgressChanged(
                    mSeekBar,
                    mProgress,
                    mProgress.formatCurrency(),
                    fromUser
                )
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                listener?.onStartTrackingTouch(mSeekBar, mProgress, mProgress.formatCurrency())
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                listener?.onStopTrackingTouch(mSeekBar, mProgress, mProgress.formatCurrency())
            }
        })
    }

    private fun refreshSeekProgress(enableStep: Boolean = false) {
        if (enableStep)
            handleStepProgress()

        mSeekBar.apply {
            max = mMax.roundToInt() - mMin.roundToInt()
            progress = mProgress.roundToInt() - mMin.roundToInt()
        }
    }

    private fun handleStepProgress() {
        val progress = mProgress.toInt()
        val max = mMax.toInt()
        val step = step.invoke()

        val (leftStepPoint, rightStepPoint) = findStepPointsBetweenProgress(progress, step, max)

        val minLeft = progress - leftStepPoint
        val minRight = rightStepPoint - progress
        val nearestStepPoint = min(minLeft, minRight)

        mProgress = when (nearestStepPoint) {
            minRight -> rightStepPoint.toDouble()
            else -> leftStepPoint.toDouble()
        }
    }

    private fun findStepPointsBetweenProgress(progress: Int, steps: Int, max: Int): Pair<Int, Int> {
        for (i in 0..max step steps) {
            if (i >= progress) {
                val leftStep = i - steps
                return leftStep to i
            }
        }

        return max to max
    }

    private fun refreshMaxTextView() {
        maxLabel.text = numberFormat.format(mMax)
    }

    private fun refreshMinTextView() {
        minLabel.text = numberFormat.format(mMin)
    }

    private fun Double.formatCurrency(): String {
        return numberFormat.format(this)
    }

    private fun Number.roundToDouble(): Double {
        return this.toDouble().roundToInt().toDouble()
    }

    companion object {
        private const val DEFAULT_MAX = 100.0
        private const val DEFAULT_MIN = 0.0
        private const val DEFAULT_PROGRESS = 50.0
        private const val DEFAULT_ENABLE_STEP = true
        private const val DEFAULT_ENABLE_ROUND = false
    }
}