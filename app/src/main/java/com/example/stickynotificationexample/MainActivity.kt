package com.example.stickynotificationexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.stickynotificationexample.NotificationHelper.createNotificationChannel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<AppCompatButton>(R.id.btnNotification)
        createNotificationChannel(CHANNEL_ID)
        button.setOnClickListener {
            val builder = NotificationHelper.getNotificationBuilder(context = this)
            NotificationHelper.triggerNotification(builder)
        }
    }

    companion object {
        const val STICKY_NOTIFICATION_ID: Int = 83761
        const val CHANNEL_ID = "channel_id"
    }
}
