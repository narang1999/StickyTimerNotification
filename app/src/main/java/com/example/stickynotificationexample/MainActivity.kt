package com.example.stickynotificationexample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.stickynotificationexample.NotificationHelper.createNotificationChannel

class MainActivity : AppCompatActivity() {
    @Volatile
    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<AppCompatButton>(R.id.btnNotification)
        createNotificationChannel(CHANNEL_ID)

        val t2 = Thread({
            for (i in 0 until 2000) {
                inc()
                //    Log.i("MainActivity123", Thread.currentThread().state.toString() + i.toString())
            }
        }, "second")
        val t1 = Thread({
            for (i in 0 until 2000) {
                inc()
                //      Log.i("MainActivity123", Thread.currentThread().state.toString() + i.toString())
                //    Log.i("MainActivity1234", t2.state.toString())
            }
        }, "First")
        t1.start()
        t2.start()
        t1.join()
        t2.join()
        Log.i("MainActivity12345", count.toString())

        button.setOnClickListener {
            val builder = NotificationHelper.getNotificationBuilder(context = this)
            NotificationHelper.triggerNotification(builder)
        }
    }
    fun inc() {
        synchronized(this) {
            count++
        }
    }
    companion object {
        const val STICKY_NOTIFICATION_ID: Int = 8376
        const val CHANNEL_ID = "channel_id"
    }
}
