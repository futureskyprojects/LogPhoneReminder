package me.vistark.fastdroid_lib.utils

import android.content.Context
import es.dmoral.toasty.Toasty

object FastToasty {
    fun Context.ToastError(msg: String, isWithIcon: Boolean = true) {
        Toasty.error(this, msg, Toasty.LENGTH_SHORT, isWithIcon).show()
    }

    fun Context.ToastSuccess(msg: String, isWithIcon: Boolean = true) {
        Toasty.success(this, msg, Toasty.LENGTH_SHORT, isWithIcon).show()
    }

    fun Context.ToastWarning(msg: String, isWithIcon: Boolean = true) {
        Toasty.warning(this, msg, Toasty.LENGTH_SHORT, isWithIcon).show()
    }

    fun Context.ToastInfo(msg: String, isWithIcon: Boolean = true) {
        Toasty.info(this, msg, Toasty.LENGTH_SHORT, isWithIcon).show()
    }
}