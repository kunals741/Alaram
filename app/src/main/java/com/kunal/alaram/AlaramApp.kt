package com.kunal.alaram

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kunal.alaram.ui.theme.PoppinsFontFamily
import com.kunal.alaram.ui.theme.darkTextColor
import com.kunal.alaram.utils.CalendarHelperUtil
import kotlinx.coroutines.delay
import java.util.Calendar

@Composable
fun Alaram(modifier: Modifier) {

    val calendar = Calendar.getInstance()
    var timeString by remember { mutableStateOf(CalendarHelperUtil.convertTimeFromMillis(Calendar.getInstance().timeInMillis)) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3600L)
            timeString =
                CalendarHelperUtil.convertTimeFromMillis(Calendar.getInstance().timeInMillis)
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(0.dp, 90.dp, 0.dp, 0.dp),
            fontFamily = PoppinsFontFamily,
            fontSize = 84.sp,
            fontWeight = Medium,
            color = darkTextColor,
            text = timeString
        )
        AlarmList(Modifier)
    }
}






