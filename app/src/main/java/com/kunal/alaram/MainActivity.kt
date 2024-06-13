package com.kunal.alaram

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kunal.alaram.ui.theme.AlaramTheme

class MainActivity : ComponentActivity() {
    companion object {
        const val REQUEST_OVERLAY_PERMISSION = 100
        const val REQUEST_ALARM_PERMISSION = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            AlaramTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Alaram(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_OVERLAY_PERMISSION) {
            if (Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "Overlay Permission Granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Please Grant Alert Window Permission", Toast.LENGTH_LONG)
                    .show()
            }
        }else if(requestCode == REQUEST_ALARM_PERMISSION){
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && alarmManager.canScheduleExactAlarms()) {
                Toast.makeText(this, "Alarmy Permission Granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Please Grant Alarm Permission", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}

@Composable
fun OverlayPermissionDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
){
    AlertDialog(
        title = {
            Text(text = "Grant Overlay Permission")
        },
        text = {
            Text(text = "This app needs overlay permission to show alarm notification")
        },
        onDismissRequest = {
          // not dismissable
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
