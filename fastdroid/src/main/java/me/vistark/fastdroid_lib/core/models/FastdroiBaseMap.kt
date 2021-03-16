package me.vistark.fastdroid_lib.core.models

import com.google.gson.annotations.SerializedName

class FastdroiBaseMap(
    @SerializedName("key")
    var key: String = "",
    @SerializedName("value")
    var value: String = ""
) {
}