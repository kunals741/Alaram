package com.kunal.alaram

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getSystemService
import com.kunal.alaram.broadcastReceivers.AlarmReceiver
import com.kunal.alaram.ui.theme.PoppinsFontFamily
import com.kunal.alaram.ui.theme.darkTextColor
import java.util.Calendar

@Composable
fun AlarmList(modifier: Modifier) {

    val calendar = Calendar.getInstance()
    val currentHour = calendar[Calendar.HOUR_OF_DAY]
    val currentMinute = calendar[Calendar.MINUTE]
    val context = LocalContext.current
    var selectedTimeInMillis by remember { mutableStateOf<Long?>(null) }

    val picker =
        TimePickerDialog(
            LocalContext.current,
            { _, hour: Int, minute: Int ->
                //to get selected time in millis
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                setAlarm(context, calendar.timeInMillis)
                selectedTimeInMillis = calendar.timeInMillis
            }, currentHour, currentMinute, false
        )

    Column(
        modifier = Modifier
    ) {
        Text(
            text = stringResource(R.string.alarms),
            fontFamily = PoppinsFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            color = darkTextColor,
            modifier = Modifier.padding(24.dp, 24.dp, 0.dp, 24.dp)
        )

        LazyColumn {
            items(1) {
                selectedTimeInMillis?.let { it1 -> AlarmCard(modifier, it1) }
            }
        }

        Spacer(
            modifier = Modifier.weight(1f)
        )

        ElevatedCard(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 40.dp),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 10.dp
            ),
            onClick = {
                picker.show()
            }
        ) {
            Text(
                text = "Add New",
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 22.sp,
                color = darkTextColor,
                modifier = Modifier.padding(40.dp, 14.dp)
            )
        }
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


@Preview(showBackground = true, widthDp = 360)
@Composable
fun AlarmListPreview() {
    AlarmList(Modifier)
}