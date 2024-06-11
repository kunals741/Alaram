package com.kunal.alaram.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.util.Log
import android.widget.Toast
import com.kunal.alaram.MainActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("TAG", "Alarm Triggerd")
        Toast.makeText(context, "Alarm Triggered", Toast.LENGTH_SHORT).show()
        val pm = context?.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakeLock = pm.newWakeLock(
            PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.ON_AFTER_RELEASE,
            "myapp:ALARM_WAKE_LOCK"
        )
        wakeLock.acquire(10*60*1000L /*10 minutes*/)
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("IS_NOTIFICATION", true)
            setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
        wakeLock.release()
    }
}