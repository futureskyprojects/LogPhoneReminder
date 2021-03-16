package me.vistark.fastdroid_lib.utils

import android.os.Handler
import android.os.Looper

object TimerUtils {
    fun startAfter(delayedTime: Long, func: () -> Unit) {
        Handler(Looper.myLooper()!!).postDelayed({
            func.invoke()
        }, delayedTime)
    }
}