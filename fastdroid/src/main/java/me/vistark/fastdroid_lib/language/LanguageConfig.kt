package me.vistark.fastdroid_lib.language

import me.vistark.fastdroid_lib.utils.storage.AppStorageManager

object LanguageConfig {
    var LanguageCode: String = "en"
        get() = AppStorageManager.get("LANGUAGE_CODE") ?: field
        set(value) {
            AppStorageManager.update("LANGUAGE_CODE", value)
            field = value
        }
}