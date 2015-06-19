package com.cloudtime.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.cloudtime.alarm.AlarmAndroidService

class FireAlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        notifyAlarm()
        context.startService(Intent(context, javaClass<AlarmAndroidService>()))
    }

    private fun notifyAlarm() {
        Log.e("tag", "my_tag notify!")
    }
}
