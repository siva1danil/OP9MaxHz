package siva1danil.op9maxhz

import android.content.Context

class RefreshRateRepository(context: Context) {
    private val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    fun getRate(): Float = prefs.getFloat("rate", 120.0f)
    fun setRate(value: Float) = prefs.edit().putFloat("rate", value).apply()
}