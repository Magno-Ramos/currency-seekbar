package com.app.currency_seekbar

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import android.content.Context
import junit.framework.TestCase.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class CurrencySeekBarTest {

    private val context: Context = InstrumentationRegistry.getInstrumentation().context

    @Test
    fun testMinLabelVisibility() {
        val view = CurrencySeekBar(context)

        view.showMinLabel(false)
        assertFalse(view.minLabelIsShow())

        view.showMinLabel(true)
        assertTrue(view.minLabelIsShow())
    }

    @Test
    fun testMaxLabelVisibility() {
        val view = CurrencySeekBar(context)

        view.showMaxLabel(false)
        assertFalse(view.maxLabelIsShow())

        view.showMaxLabel(true)
        assertTrue(view.maxLabelIsShow())
    }

    @Test
    fun whenSetProgressBelowMin_shouldUseMin() {
        val view = CurrencySeekBar(context)
        val expected = 200.0

        view.setMax(400.0)
        view.setMin(200.0)
        view.setProgress(100.0)

        assertEquals(expected, view.getProgress())
    }

    @Test
    fun whenProgressAboveMax_shouldChangeProgressToMax() {
        val view = CurrencySeekBar(context)
        val expected = 600.0

        view.setMax(600.0)
        view.setMin(200.0)
        view.setProgress(1200.0)

        assertEquals(expected, view.getProgress())
    }

    @Test
    fun testRoundProgress() {
        val view = CurrencySeekBar(context)
        view.setMax(200)
        view.setMin(100)
        view.setEnableStep(true)

        var expected = 120.0
        view.setEnableRound(true)
        view.setProgress(119.56)
        assertEquals(expected, view.getProgress())

        expected = 112.21
        view.setEnableRound(false)
        view.setProgress(112.21)
        assertEquals(expected, view.getProgress())
    }
}