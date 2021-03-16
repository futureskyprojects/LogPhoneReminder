package me.vistark.fastdroid_lib.utils

import com.google.gson.Gson

object JsonHandler {
    // Chuyển đối tượng thành chuỗi Json
    fun Any.toJson(): String {
        return Gson().toJson(this)
    }

    // Từ chuỗi Json chuyển thành đối tượng có kiểu
    fun <T> String.toTyped(objectType: T): T {
        return Gson().fromJson(this, objectType!!::class.java)
    }
}