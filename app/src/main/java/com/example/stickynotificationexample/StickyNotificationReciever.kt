package com.example.stickynotificationexample

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class StickyNotificationReciever : BroadcastReceiver() {
    private val notificationManager
        get() = MyApplication.appContext?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == StickyNotificationBuilder.STICKY_NOTIFICATION_ACTION) {
            notificationManager.cancel(MainActivity.STICKY_NOTIFICATION_ID)
        }
    }
}
