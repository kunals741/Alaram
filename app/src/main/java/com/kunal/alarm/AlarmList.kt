package com.kunal.alarm

import android.app.AlarmManager
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getSystemService
import com.kunal.alarm.MainActivity.Companion.REQUEST_ALARM_PERMISSION
import com.kunal.alarm.model.AlarmData
import com.kunal.alarm.ui.theme.PoppinsFontFamily
import com.kunal.alarm.ui.theme.darkTextColor
import com.kunal.alarm.utils.AlarmUtil
import com.kunal.alarm.utils.CalendarHelperUtil
import java.util.Calendar

@Composable
fun AlarmList(modifier: Modifier) {

    val calendar = Calendar.getInstance()
    val context = LocalContext.current
    val alarmList = remember { mutableStateListOf<AlarmData>() }
    val alarmManager = getSystemService(context, AlarmManager::class.java)
    //bug : shows last time selected, after selecting one

    val picker =
        TimePickerDialog(
            LocalContext.current,
            { _, hour: Int, minute: Int ->
                val newCalendar = Calendar.getInstance()
                newCalendar.set(Calendar.HOUR_OF_DAY, hour)
                newCalendar.set(Calendar.MINUTE, minute)
                newCalendar.set(Calendar.SECOND, 0)
                newCalendar.set(Calendar.MILLISECOND, 0)
                alarmList.add((AlarmData((alarmList.size+1), newCalendar.timeInMillis)))
                if (alarmManager != null) {
                    AlarmUtil.setAlarm(context, alarmManager, newCalendar.timeInMillis, alarmList.size)
                    Toast.makeText(
                        context,
                        "Alarm Set at ${CalendarHelperUtil.convertTimeFromMillis(alarmList.last().time)}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }, calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE], false
        )

    Column(
        modifier = Modifier
    ) {
        if (alarmList.size > 0) {
            Text(
                text = stringResource(R.string.alarms),
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                color = darkTextColor,
                modifier = Modifier.padding(24.dp, 24.dp, 0.dp, 24.dp)
            )
        }

        LazyColumn (
            modifier = Modifier.weight(1f)
        ){
            items(items = alarmList) { alarm ->
                AlarmCard(modifier, alarm) { newAlarm ->
                    val index = alarmList.indexOfFirst { it.id == newAlarm.id }
                    if (index != -1) {
                        alarmList[index] = newAlarm
                    }
                    //cancel previous alarm and set alarm with new time:
                    if (alarmManager != null) {
                        AlarmUtil.setAlarm(context, alarmManager, newAlarm.time, newAlarm.id)
                    }
                }
            }
        }

        ElevatedCard(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 40.dp),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 10.dp
            ),
            onClick = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && alarmManager?.canScheduleExactAlarms() == true) {
                    picker.show()
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    val intent = Intent(
                        ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
                        Uri.parse("package:${context.packageName}")
                    )
                    (context as MainActivity).startActivityForResult(
                        intent,
                        REQUEST_ALARM_PERMISSION
                    )
                }
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

@Preview(showBackground = true, widthDp = 360)
@Composable
fun AlarmListPreview() {
    AlarmList(Modifier)
}