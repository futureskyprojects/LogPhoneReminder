package me.vistark.fastdroid_lib.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.PowerManager


object WakeLock {
    @SuppressLint("InvalidWakeLockTag")
    fun Context.wakeLock(): PowerManager.WakeLock? {
        val pm = this.getSystemService(Context.POWER_SERVICE) as PowerManager?
        val wakelock = pm!!.newWakeLock(
            PowerManager.FULL_WAKE_LOCK or
                    PowerManager.ACQUIRE_CAUSES_WAKEUP or
                    PowerManager.ON_AFTER_RELEASE, "WakeLock"
        )
        wakelock.acquire(60 * 1000L)
        return wakelock
    }
}