package me.vistark.fastdroid_lib.utils

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import me.vistark.fastdroid_lib.core.models.FastdroiBaseMap
import me.vistark.fastdroid_lib.utils.storage.AppStorageManager

object MultipleLanguage {
    var Translates: Array<FastdroiBaseMap>
        get() = AppStorageManager.get("APP_DINAMIC_TRANSLATE") ?: emptyArray()
        set(value) {
            AppStorageManager.update("APP_DINAMIC_TRANSLATE", value)
        }

    private fun String.trueParams(): String {
        return this
            .replace("__d__", "%d")
            .replace("__s__", "%s")
    }

    fun L(key: String): String {
        if (key.isEmpty())
            return ""
        return Translates.firstOrNull {
            it.key.trueParams().equals(key.trueParams(), ignoreCase = true)
        }?.value ?: "$key"
    }

    fun View.autoTranslate() {
        // Nếu là Edittext thì tự động chuyển đổi hint và giữ nguyên nội dung
        if (this is EditText) {
            this.hint = L(this.hint.toString())
            return
        }

        // Nếu nó là TextView thì tự động chuyển đổi trên văn bản
        if (this is TextView) {
            this.text = L(this.text.toString())
            try {
                this.hint = L(this.hint.toString())
            } catch (e: Exception) {
            }
            return
        }

        //Nếu là một nhóm layout thì đệ quy vào các phần tử con
        if (this is ViewGroup) {
            for (i in 0 until childCount) {
                getChildAt(i).autoTranslate()
            }
        }
    }
}