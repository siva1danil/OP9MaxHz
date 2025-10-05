package siva1danil.op9maxhz

import android.content.Context
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.core.content.ContextCompat

/*
NOTE: There are mentions of `user_refresh_rate` key, however it doesn't seem to
have any effect on screen refresh rate. Use these snippets if it is in fact used:

private const val KEY_USER_REFRESH_RATE = "user_refresh_rate"
private fun getUserRefreshRate(context: Context): Float? {
    return try {
        return Settings.System.getFloat(context.contentResolver, KEY_USER_REFRESH_RATE)
    } catch (e: Settings.SettingNotFoundException) {
        null
    }
}
private fun setUserRefreshRate(context: Context, value: Float) {
    val ok = Settings.System.putFloat(context.contentResolver, KEY_USER_REFRESH_RATE, value)
    if (!ok) throw IllegalStateException("Failed to set $KEY_USER_REFRESH_RATE")
}
*/

object MaxHzUtil {
    private const val KEY_OPLUS_CUSTOMIZE_SRR = "oplus_customize_screen_refresh_rate"
    private const val KEY_PEAK_REFRESH_RATE = "peak_refresh_rate"
    private const val KEY_MIN_REFRESH_RATE = "min_refresh_rate"

    /* Permission */

    fun hasWriteSecureSettingsPermission(context: Context): Boolean {
        val perm = "android.permission.WRITE_SECURE_SETTINGS"
        return ContextCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED
    }

    /* Modes */

    fun isEnabled(context: Context): Boolean {
        val oplusCustomizeScreenRefreshRate = getOplusCustomizeScreenRefreshRate(context)
        val peakRefreshRate = getPeakRefreshRate(context)
        val minRefreshRate = getMinRefreshRate(context)
        return oplusCustomizeScreenRefreshRate == 0 && (peakRefreshRate != null && peakRefreshRate > 0.0f && peakRefreshRate < 19.999f) && (minRefreshRate == null || minRefreshRate < 19.999f)
    }

    fun setEnabled(context: Context) {
        setOplusCustomizeScreenRefreshRate(context, 0)
        Thread.sleep(100)
        setPeakRefreshRate(context, 1.0f)
        Thread.sleep(100)
        setMinRefreshRate(context, 0.0f)
    }

    fun setDisabled60(context: Context) {
        setOplusCustomizeScreenRefreshRate(context, 2)
        Thread.sleep(100)
        setPeakRefreshRate(context, 60.0f)
        Thread.sleep(100)
        setMinRefreshRate(context, 0.0f)
    }

    fun setDisabled120(context: Context) {
        setOplusCustomizeScreenRefreshRate(context, 3)
        Thread.sleep(100)
        setPeakRefreshRate(context, 120.0f)
        Thread.sleep(100)
        setMinRefreshRate(context, 0.0f)
    }

    /* Settings */

    private fun getOplusCustomizeScreenRefreshRate(context: Context): Int? {
        return try {
            return Settings.Secure.getInt(context.contentResolver, KEY_OPLUS_CUSTOMIZE_SRR)
        } catch (_: Settings.SettingNotFoundException) {
            null
        }
    }

    private fun setOplusCustomizeScreenRefreshRate(context: Context, value: Int) {
        val ok = Settings.Secure.putInt(context.contentResolver, KEY_OPLUS_CUSTOMIZE_SRR, value)
        if (!ok) throw IllegalStateException("Failed to set $KEY_OPLUS_CUSTOMIZE_SRR")
    }

    private fun getPeakRefreshRate(context: Context): Float? {
        return try {
            Settings.System.getFloat(context.contentResolver, KEY_PEAK_REFRESH_RATE)
        } catch (_: Settings.SettingNotFoundException) {
            null
        }
    }

    private fun setPeakRefreshRate(context: Context, value: Float) {
        val ok = Settings.System.putFloat(context.contentResolver, KEY_PEAK_REFRESH_RATE, value)
        if (!ok) throw IllegalStateException("Failed to set $KEY_PEAK_REFRESH_RATE")
    }

    private fun getMinRefreshRate(context: Context): Float? {
        return try {
            return Settings.System.getFloat(context.contentResolver, KEY_MIN_REFRESH_RATE)
        } catch (_: Settings.SettingNotFoundException) {
            null
        }
    }

    private fun setMinRefreshRate(context: Context, value: Float) {
        val ok = Settings.System.putFloat(context.contentResolver, KEY_MIN_REFRESH_RATE, value)
        if (!ok) throw IllegalStateException("Failed to set $KEY_MIN_REFRESH_RATE")
    }
}
