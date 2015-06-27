package com.cloudtime.alarm

import android.R
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import com.cloudtime.ui.MainActivity


class FireAlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        notifyAlarm(context)
        context.startService(Intent(context, javaClass<AlarmAndroidService>()))
    }

    private fun notifyAlarm(context: Context) {
        Log.e("tag", "my_tag notify! notifyAlarm!")
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val msg = "Timer finished!"
        val notification = Notification(R.drawable.btn_plus, msg, java.lang.System.currentTimeMillis())

        notification.flags =  notification.flags or Notification.FLAG_SHOW_LIGHTS
        notification.flags =  notification.flags or Notification.FLAG_INSISTENT
//        notification.flags =  notification.flags or Notification.DEFAULT_VIBRATE
        notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        notification.flags =  notification.flags or Notification.DEFAULT_SOUND
        notification.flags =  notification.flags or Notification.FLAG_AUTO_CANCEL
        notification.vibrate = longArrayOf(300, 300, 300, 300, 300)

        val notificationIntent = Intent(context, javaClass<MainActivity>())
        val contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)

        notification.setLatestEventInfo(context, msg, msg, contentIntent)
        notificationManager.notify(1, notification)

    }
}
