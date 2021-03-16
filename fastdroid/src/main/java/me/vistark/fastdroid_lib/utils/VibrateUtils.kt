package me.vistark.fastdroid_lib.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import me.vistark.fastdroid_lib.utils.VibrateUtils.vibrate


object VibrateUtils {
    fun Context.vibrate(millis: Long = 500) {
        val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v?.vibrate(VibrationEffect.createOneShot(millis, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            v?.vibrate(millis)
        }
    }

    fun Context.vibrateLoop(
        longArray: LongArray = longArrayOf(1000, 500, 700, 500, 1000),
        limit: Int = 0
    ): Vibrator? {
        val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v?.vibrate(
                VibrationEffect.createWaveform(
                    longArray,
                    limit
                )
            )
        } else {
            v?.vibrate(longArray, limit)
        }

        return v
    }

    fun View.vibrate(millis: Long = 500) {
        context.vibrate(millis)
    }
}