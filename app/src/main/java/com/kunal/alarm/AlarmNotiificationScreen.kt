package com.kunal.alarm

import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kunal.alarm.ui.theme.PoppinsFontFamily
import com.kunal.alarm.utils.CalendarHelperUtil
import java.util.Calendar

@Composable
fun AlarmNotification(modifier: Modifier, onDismiss: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.drawable_notification_bg),
                contentScale = ContentScale.FillBounds
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text =CalendarHelperUtil.convertTimeFromMillis(Calendar.getInstance().timeInMillis),
            fontFamily = PoppinsFontFamily,
            fontSize = 48.sp,
            color = Color.White,
            modifier = Modifier.padding(top = 48.dp)
        )

        Text(
            text = "Alarm",
            fontFamily = PoppinsFontFamily,
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(Modifier.weight(1f))

        Image(
            painterResource(R.drawable.ic_close), contentDescription = "Close",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 50.dp)
                .clickable {
                    onDismiss()
                }
        )
    }
}

@Composable
@Preview
fun AlarmNotificationPreview() {
    AlarmNotification(Modifier) {
    }
}