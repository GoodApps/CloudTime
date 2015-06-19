package com.cloudtime.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.cloudtime.alarm.FireAlarmBroadcastReceiver
import com.cloudtime.dto.Timer
import com.cloudtime.android.AlarmManagerCompat
import java.util.Date

class AlarmService(private val context: Context) {

    private val am = AlarmManagerCompat(context)

    fun createAlarm(timers: List<Timer>) {
        val sorted = timers.sortBy(timerEndsAt())
        val next = sorted.firstOrNull(closestTimer())
        Log.e("tag", "my_tag ${next}")
        if (next != null) {
            am.set(AlarmManager.RTC_WAKEUP, next.endsAt(), createPendingIntent())
        } else {
            am.cancel(createPendingIntent())
        }
    }

    private fun timerEndsAt(): (Timer) -> Long = { if (it.started()) it.endsAt() else Long.MAX_VALUE }

    private fun closestTimer(): (Timer) -> Boolean = { it.started() && Date(it.endsAt()).after(Date()) }

    private fun createPendingIntent(): PendingIntent {
        val intent = Intent(context, javaClass<FireAlarmBroadcastReceiver>())
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }
}
