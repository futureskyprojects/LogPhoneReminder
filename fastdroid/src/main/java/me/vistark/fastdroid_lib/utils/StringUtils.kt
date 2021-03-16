package me.vistark.fastdroid_lib.utils

import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

object StringUtils {
    fun String.like(regexStr: String, groupId: Int = 0): String {
        val pattern: Pattern = Pattern.compile(regexStr)
        val matcher: Matcher = pattern.matcher(this)
        if (matcher.find()) {
            return matcher.group(groupId) ?: ""
        }
        return ""
    }

    fun Guid(): String {
        return UUID.randomUUID().toString()
    }
}