package com.cloudtime.android

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build

class AlarmManagerCompat(context: Context) {

    private val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun set(type: Int, triggerAtMillis: Long, operation: PendingIntent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            am.setExact(type, triggerAtMillis, operation)
        } else {
            am.set(type, triggerAtMillis, operation)
        }
    }

    fun cancel(operation: PendingIntent) {
        am.cancel(operation)
    }
}
