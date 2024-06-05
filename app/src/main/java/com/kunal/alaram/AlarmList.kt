package com.kunal.alaram

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kunal.alaram.ui.theme.PoppinsFontFamily
import com.kunal.alaram.ui.theme.darkTextColor

@Composable
fun AlarmList(modifier: Modifier) {
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

        AlarmCard(modifier)
        AlarmCard(modifier)
        AlarmCard(modifier)

        Spacer(
            modifier = Modifier.weight(1f)
        )

        ElevatedCard(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 40.dp),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 10.dp
            )
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