package com.cloudtime.alarm

import android.app.AlarmManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.support.v4.content.WakefulBroadcastReceiver
import android.util.Log
import com.cloudtime.dto.Timer
import com.cloudtime.alarm.AlarmService
import com.cloudtime.service.TimerService

public class AlarmAndroidService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        TimerService().loadTimers()
                .subscribe({ createAlarm(it, intent) }, { handleError(it, intent) })
        return Service.START_STICKY
    }

    private fun createAlarm(timers: List<Timer>, intent: Intent?) {
        AlarmService(this).createAlarm(timers)
        WakefulBroadcastReceiver.completeWakefulIntent(intent)
    }

    private fun handleError(t: Throwable, intent: Intent?) {
        Log.e("tag", "error", t)
        WakefulBroadcastReceiver.completeWakefulIntent(intent)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
