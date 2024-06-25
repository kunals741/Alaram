package com.kunal.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getSystemService
import com.kunal.alarm.broadcastReceivers.AlarmReceiver
import com.kunal.alarm.model.AlarmData
import com.kunal.alarm.ui.theme.PoppinsFontFamily
import com.kunal.alarm.ui.theme.darkTextColor
import com.kunal.alarm.ui.theme.startGradientToggleOnColor
import com.kunal.alarm.utils.CalendarHelperUtil
import java.util.Calendar


@Composable
fun AlarmCard(modifier: Modifier, alarmDetails: AlarmData, onClick: (AlarmData) -> Unit) {
    var checked by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val alarmManager = getSystemService(context, AlarmManager::class.java)

    val picker =
        TimePickerDialog(
            LocalContext.current,
            { _, hour: Int, minute: Int ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                if (alarmManager != null) {
                    setAlarm(context, alarmManager, calendar.timeInMillis, alarmDetails.id)
                }
                onClick(alarmDetails.copy(id = alarmDetails.id, time = calendar.timeInMillis))
            }, calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE], false
        )

    ElevatedCard(
        modifier = modifier.padding(20.dp, 0.dp, 20.dp, 24.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 10.dp
        ),
        onClick = {
            picker.show()
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                CalendarHelperUtil.convertTimeFromMillis(alarmDetails.time),
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 34.sp,
                color = darkTextColor,
                modifier = Modifier.padding(16.dp, 24.dp, 0.dp, 24.dp)
            )

            Spacer(
                modifier = Modifier
                    .weight(1f)
            )

            Switch(
                checked = checked,
                onCheckedChange = {
                    val intent = Intent(context, AlarmReceiver::class.java).apply {
                        putExtra("alarm_time", alarmDetails.time)
                    }
                    val pendingIntent = PendingIntent.getBroadcast(
                        context, alarmDetails.id, intent,
                        PendingIntent.FLAG_IMMUTABLE
                    )
                    if (it) {
                        if (alarmManager != null) {
                            setAlarm(context, alarmManager, alarmDetails.time, alarmDetails.id)
                        }
                        Toast.makeText(context, "Alarm Set", Toast.LENGTH_SHORT).show()
                    } else {
                        alarmManager?.cancel(pendingIntent)
                        Toast.makeText(context, "Alarm Cancelled", Toast.LENGTH_SHORT).show()
                    }
                    checked = it
                },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = startGradientToggleOnColor,
                    uncheckedThumbColor = Color.White,
                ),
                modifier = Modifier
                    .padding(end = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlarmCardPreview() {
    AlarmCard(Modifier, AlarmData(1, Calendar.getInstance().timeInMillis)) {

    }
}