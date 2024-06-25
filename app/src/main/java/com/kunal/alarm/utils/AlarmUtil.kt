package com.kunal.alarm.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import com.kunal.alarm.broadcastReceivers.AlarmReceiver
import com.kunal.alarm.model.AlarmData

class AlarmUtil {
    companion object {
        @JvmStatic
        fun cancelAlarm(
            context: Context,
            alarmManager: AlarmManager,
            alarmDetails: AlarmData
        ) {
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context, alarmDetails.id, intent,
                PendingIntent.FLAG_MUTABLE
            )
            alarmManager.cancel(pendingIntent)
            Toast.makeText(context, "Alarm Cancelled", Toast.LENGTH_SHORT).show()
        }

        @JvmStatic
        fun setAlarm(
            context: Context,
            alarmManager: AlarmManager,
            selectedTimeInMillis: Long,
            requestCode: Int
        ) {
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context, requestCode, intent,
                PendingIntent.FLAG_MUTABLE
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    selectedTimeInMillis,
                    pendingIntent
                )
            }
        }
    }
}