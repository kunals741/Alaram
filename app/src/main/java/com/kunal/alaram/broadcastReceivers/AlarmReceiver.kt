package com.kunal.alaram.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.kunal.alaram.AlarmNotification
import com.kunal.alaram.utils.CalendarHelperUtil.Companion.convertTimeFromMillis

class AlarmReceiver : BroadcastReceiver() {

    private var composeView: ComposeView? = null
    private var windowManager: WindowManager? = null
    private var alarmTime : Long? = null
    private var vibrator : Vibrator? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("TAG", "Alarm Triggerd")
        alarmTime = intent?.getLongExtra("alarm_time", -1)
        showSystemAlertWindow(context)
    }

    private fun showSystemAlertWindow(context: Context?) {
        windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager: VibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            // backward compatibility for Android API < 31,
            // VibratorManager was only added on API level 31 release.
            // noinspection deprecation
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        params.gravity = Gravity.CENTER_HORIZONTAL

        composeView = ComposeView(context).apply {
            setContent {
                AlarmNotification(Modifier, convertTimeFromMillis(alarmTime!!)) {
                    windowManager?.removeView(composeView)
                    composeView = null
                    windowManager = null
                    vibrator?.cancel()
                    vibrator = null
                }
            }
        }

        //providing lifecycle owner
        val lifecycleOwner = MyLifecycleOwner()
        lifecycleOwner.performRestore(null)
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        composeView?.setViewTreeLifecycleOwner(lifecycleOwner)
        composeView?.setViewTreeSavedStateRegistryOwner(lifecycleOwner)
        vibrateDevice()
        windowManager?.addView(composeView, params)
    }

    internal class MyLifecycleOwner : SavedStateRegistryOwner {

        private var mLifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)
        private var mSavedStateRegistryController: SavedStateRegistryController =
            SavedStateRegistryController.create(this)

        val isInitialized: Boolean
            get() = true

        fun setCurrentState(state: Lifecycle.State) {
            mLifecycleRegistry.currentState = state
        }

        fun handleLifecycleEvent(event: Lifecycle.Event) {
            mLifecycleRegistry.handleLifecycleEvent(event)
        }

        fun performRestore(savedState: Bundle?) {
            mSavedStateRegistryController.performRestore(savedState)
        }

        fun performSave(outBundle: Bundle) {
            mSavedStateRegistryController.performSave(outBundle)
        }

        override val lifecycle: Lifecycle
            get() = mLifecycleRegistry
        override val savedStateRegistry: SavedStateRegistry
            get() = mSavedStateRegistryController.savedStateRegistry
    }

    private fun vibrateDevice() {
        val DELAY = 0
        val VIBRATE = 1000
        val SLEEP = 1000
        val START = 0
        val vibratePattern = longArrayOf(DELAY.toLong(), VIBRATE.toLong(), SLEEP.toLong())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(VibrationEffect.createWaveform(vibratePattern, START))
        } else {
            // backward compatibility for Android API < 26
            // noinspection deprecation
            vibrator?.vibrate(vibratePattern, START)
        }
    }
}

