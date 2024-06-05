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

    val calendar = Calendar.getInstance()

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
        AlarmList(Modifier)
    }
}






