package com.kunal.alarm.utils

import java.util.Calendar

class CalendarHelperUtil {
    companion object {
        @JvmStatic
        fun convertTimeFromMillis(timeInMillis: Long): String {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timeInMillis
            val currHour = calendar[Calendar.HOUR_OF_DAY]
            val currMinute = calendar[Calendar.MINUTE]
            val currSecond = calendar[Calendar.SECOND]
            return String.format("%02d:%02d", currHour, currMinute)
        }
    }
}