package com.cloudtime.alarm

import android.content.Context
import android.content.Intent
import android.support.v4.content.WakefulBroadcastReceiver
import android.util.Log

public class OnBootBroadcastReceiver : WakefulBroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.i("OnBootBroadcastReceiver", "my_tag just booting")
        val serviceIntent = Intent(context, javaClass<AlarmAndroidService>())
        WakefulBroadcastReceiver.startWakefulService(context, serviceIntent)
    }
}
