package com.kunal.alaram

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kunal.alaram.ui.theme.PoppinsFontFamily

@Composable
fun AlarmNotification(modifier: Modifier, alarmTime: String, onDismiss: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            "Alarm Notification",
            fontFamily = PoppinsFontFamily,
            fontSize = 36.sp,
            modifier = Modifier.padding(top = 48.dp)
        )

        Text(
            text = alarmTime,
            fontFamily = PoppinsFontFamily,
            fontSize = 36.sp,
            modifier = Modifier.padding(top = 48.dp)
        )

        Spacer(Modifier.weight(1f))

        Image(
            painterResource(R.drawable.close), contentDescription = "Close",
            modifier = Modifier
                .size(100.dp)
                .padding(bottom = 48.dp)
                .clickable {
                    onDismiss()
                }
        )
    }
}

@Composable
@Preview
fun AlarmNotificationPreview() {
    AlarmNotification(Modifier, "15:26") {

    }
}