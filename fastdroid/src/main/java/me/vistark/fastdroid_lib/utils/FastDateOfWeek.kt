package me.vistark.fastdroid_lib.utils

import java.util.*

class FastDateOfWeek {
    companion object {
        fun initNameOfDay(): Array<String> {
            return initNameOfDay(
                "Sunday",
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday"
            )
        }

        private fun initNameOfDay(
            Sunday: String,
            Monday: String,
            Tuesday: String,
            Wednesday: String,
            Thursday: String,
            Friday: String,
            Saturday: String,
        ): Array<String> {
            return arrayOf("", Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday)
        }

        fun Date.getNameOfDay(): String {
            val cal = Calendar.getInstance()
            cal.time = this
            val day: Int = cal.get(Calendar.DAY_OF_WEEK)
            return initNameOfDay()[day]
        }

        fun Date.getNameOfDayVN(): String {
            val cal = Calendar.getInstance()
            cal.time = this
            val day: Int = cal.get(Calendar.DAY_OF_WEEK)
            return initNameOfDay("CN", "T2", "T3", "T4", "T5", "T6", "T7")[day]
        }
    }
}