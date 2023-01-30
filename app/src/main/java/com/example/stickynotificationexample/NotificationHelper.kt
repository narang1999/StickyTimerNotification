package com.example.stickynotificationexample

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.app.NotificationCompat

object NotificationHelper {
    fun getNotificationBuilder(context: Context): Notification {
        val action = NotificationCompat.Action.Builder(
            0,
            "Dismiss",
            getActionsPendingIntent(context)
        ).build()
        val builder = StickyNotificationBuilder(context, MainActivity.CHANNEL_ID)
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
        builder.setContentTitle("Notification")
        builder.setContentText("Event is going on")
        builder.addAction(action)
        builder.setTimeoutAfter(getTimeOutAfter(System.currentTimeMillis() + 28000))
        builder.setChannelId(MainActivity.CHANNEL_ID)
        builder.setOngoing(true)
        builder.setGroup("STICKY_NOTIFICATION")
        builder.setAutoCancel(true)
        val build = builder.build().apply {
            flags =
                Notification.FLAG_ONGOING_EVENT or Notification.FLAG_NO_CLEAR
        }
        return build
    }

    fun createNotificationChannel(channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel2 = NotificationChannel(
                channelId,
                channelId,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                setSound(null, null)
            }
            notificationManager.createNotificationChannel(channel2)
        }
    }

    private val notificationManager
        get() = MyApplication.appContext?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun triggerNotification(builder: Notification) {
        notificationManager.notify(MainActivity.STICKY_NOTIFICATION_ID, builder)
    }

    private fun getTimeOutAfter(endTime: Long) = endTime - System.currentTimeMillis()

    private fun getActionsPendingIntent(
        context: Context
    ): PendingIntent? {
        val intent = Intent(context, StickyNotificationReciever::class.java)
        intent.action = StickyNotificationBuilder.STICKY_NOTIFICATION_ACTION
        return PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
        )
    }
}
fun String.parseHtml(): Spanned? {
    this.let { text ->
        val decoratedData =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(text)
            }
        return decoratedData
    }
}