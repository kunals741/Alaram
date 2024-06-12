package com.kunal.alaram

import android.app.TimePickerDialog
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
import com.kunal.alaram.model.AlarmData
import com.kunal.alaram.ui.theme.PoppinsFontFamily
import com.kunal.alaram.ui.theme.darkTextColor
import com.kunal.alaram.ui.theme.startGradientToggleOnColor
import com.kunal.alaram.utils.CalendarHelperUtil
import java.util.Calendar


@Composable
fun AlarmCard(modifier: Modifier, alarmDetails: AlarmData, onClick: (AlarmData) -> Unit) {
    var checked by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val picker =
        TimePickerDialog(
            LocalContext.current,
            { _, hour: Int, minute: Int ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                setAlarm(context, calendar.timeInMillis)
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