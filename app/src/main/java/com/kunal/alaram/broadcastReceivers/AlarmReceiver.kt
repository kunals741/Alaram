package com.kunal.alaram.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
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

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("TAG", "Alarm Triggerd")
        alarmTime = intent?.getLongExtra("alarm_time", -1)
        showSystemAlertWindow(context)
    }

    private fun showSystemAlertWindow(context: Context?) {
        windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager

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
                }
            }
        }

        //providing lifecycle owner
        val lifecycleOwner = MyLifecycleOwner()
        lifecycleOwner.performRestore(null)
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        composeView?.setViewTreeLifecycleOwner(lifecycleOwner)
        composeView?.setViewTreeSavedStateRegistryOwner(lifecycleOwner)

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
}

