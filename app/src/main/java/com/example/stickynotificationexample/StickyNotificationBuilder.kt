package com.example.stickynotificationexample

import android.content.Context
import android.os.Build
import android.os.SystemClock
import android.text.Html
import android.text.Spanned
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat

class StickyNotificationBuilder(
    context: Context,
    channel: String

) :
    NotificationCompat.Builder(context, channel) {

    private var remoteExpandedViews = RemoteViews(
        context.packageName,
        R.layout.sticky_remote_expanded_view
    )

    private var remoteCollapsedViews = RemoteViews(
        context.packageName,
        R.layout.sticky_remote_collapsed_view
    )

    init {
        setCustomContentView(remoteCollapsedViews)
        setCustomHeadsUpContentView(remoteCollapsedViews)
        setCustomBigContentView(remoteExpandedViews)
        setStyle(NotificationCompat.DecoratedCustomViewStyle())
        setupUi()
    }

    private fun setupUi() {
        val endTime: Long = System.currentTimeMillis() + 30000
        remoteExpandedViews.setChronometer(
            R.id.tv_timer,
            SystemClock.elapsedRealtime() + (endTime - System.currentTimeMillis()),
            null,
            true
        )
        remoteCollapsedViews.setChronometer(
            R.id.tv_timer,
            SystemClock.elapsedRealtime() + (endTime - System.currentTimeMillis()),
            null,
            true
        )
    }

    override fun setContentTitle(title: CharSequence?): NotificationCompat.Builder {
        remoteExpandedViews.setTextViewText(
            R.id.iv_notification,
            title.toString().parseHtml()
        )
        remoteCollapsedViews.setTextViewText(
            R.id.iv_notification,
            title.toString().parseHtml()
        )
        return super.setContentTitle(title)
    }
    override fun setContentText(text: CharSequence?): NotificationCompat.Builder {
        remoteExpandedViews.setTextViewText(
            R.id.tv_notification_message,
            text.toString().parseHtml()
        )
        remoteCollapsedViews.setTextViewText(
            R.id.tv_notification_message,
            text.toString().parseHtml()
        )
        return super.setContentText(text)
    }

    companion object {
        const val STICKY_NOTIFICATION_ACTION = "notification_dismiss"
    }
}
