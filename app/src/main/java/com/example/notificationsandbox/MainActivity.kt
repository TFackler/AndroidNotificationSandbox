package com.example.notificationsandbox

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val notificationHelper = NotificationHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationHelper.createNotificationChannel()

        // get reference to button
        val btnNotification1 = findViewById<Button>(R.id.button_notification_1)
        val btnNotification2 = findViewById<Button>(R.id.button_notification_2)
        val btnNotificationDelayed = findViewById<Button>(R.id.button_notification_delayed)

        // set on-click listener
        btnNotification1.setOnClickListener {
            Toast.makeText(this@MainActivity, getString(R.string.notification_1_title), Toast.LENGTH_SHORT).show()
            notificationHelper.sendSimpleNotification(
                titleRes = R.string.notification_1_title,
                descriptionRes = R.string.notification_1_description,
            )
        }
        btnNotification2.setOnClickListener {
            Toast.makeText(this@MainActivity, getString(R.string.notification_2_title), Toast.LENGTH_SHORT).show()
            notificationHelper.sendSimpleNotification(
                titleRes = R.string.notification_2_title,
                descriptionRes = R.string.notification_2_description,
            )
        }
        btnNotificationDelayed.setOnClickListener {
            Toast.makeText(this@MainActivity, "Notification Delayed", Toast.LENGTH_SHORT).show()
            notificationHelper.sendSimpleNotification()
        }
    }
}