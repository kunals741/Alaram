package com.kunal.alaram

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getSystemService
import com.kunal.alaram.broadcastReceivers.AlarmReceiver
import com.kunal.alaram.ui.theme.PoppinsFontFamily
import com.kunal.alaram.ui.theme.darkTextColor
import java.util.Calendar

@Composable
fun Alaram(modifier: Modifier) {
    val selectedTime = remember { mutableStateOf("-") }
    var selectedTimeInMillis: Long? = null
    val calendar = Calendar.getInstance()
    val currentHour = calendar[Calendar.HOUR_OF_DAY]
    val currentMinute = calendar[Calendar.MINUTE]
    val context = LocalContext.current
    var hasPermission by remember { mutableStateOf(false) }


    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            hasPermission = true
            if (isGranted) {
                Toast.makeText(context, "Permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Please grant permissions", Toast.LENGTH_SHORT).show()
            }
        }

    val picker =
        TimePickerDialog(
            LocalContext.current,
            { _, hour: Int, minute: Int ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                selectedTimeInMillis = calendar.timeInMillis
                selectedTime.value = "$hour:$minute"
                setAlarm(context, selectedTimeInMillis!!)
            }, currentHour, currentMinute, false
        )

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val currHour = calendar[Calendar.HOUR_OF_DAY]
        val currMinute = calendar[Calendar.MINUTE]
        Text(
            modifier = Modifier.padding(0.dp, 90.dp, 0.dp, 0.dp),
            fontFamily = PoppinsFontFamily,
            fontSize = 84.sp,
            fontWeight = Medium,
            color = darkTextColor,
            text = "$currHour:$currMinute"
        )
//        Text("Selected Time: ${selectedTime.value}")
//        Button(onClick = {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(
//                    context, Manifest.permission.SCHEDULE_EXACT_ALARM
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                requestPermissionLauncher.launch(Manifest.permission.SCHEDULE_EXACT_ALARM)
//            } else {
//                picker.show()
//            }
//        }) {
//            Text("Select Time")
//        }
        AlarmList(Modifier)
    }
}

fun setAlarm(context: Context, selectedTimeInMillis: Long) {
    val alarmManager = getSystemService(context, AlarmManager::class.java)
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context, 0, intent,
        PendingIntent.FLAG_IMMUTABLE
    )
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && alarmManager?.canScheduleExactAlarms() == true) {
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            selectedTimeInMillis,
            pendingIntent
        )
    }
}




