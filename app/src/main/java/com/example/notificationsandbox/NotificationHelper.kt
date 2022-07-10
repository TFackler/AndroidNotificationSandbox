package com.example.notificationsandbox

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

/**
 * A class for wrapping necessary notification functions like setting up notifications for the
 * different levels of the android framework and wrapping the sending of simple notifications.
 */
class NotificationHelper(
    private val context: Context
) {
    companion object {
        private const val DEFAULT_CHANNEL_ID = "default"
        private const val DEFAULT_CHANNEL_NAME = "Default Channel"
        private const val DEFAULT_CHANNEL_DESCRIPTION = "The default notification channel."

        private var currentNotificationCount = 0
    }

    /**
     * Should be run at the start of the app.
     */
    fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(
                DEFAULT_CHANNEL_ID,
                DEFAULT_CHANNEL_NAME,
                importance
            ).apply {
                description = DEFAULT_CHANNEL_DESCRIPTION
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Sends a simple notification with default values.
     *
     * @return the id of the created notification
     */
    fun sendSimpleNotification(
        @DrawableRes icon: Int = R.drawable.ic_check_24,
        @StringRes titleRes: Int = R.string.notification_simple,
        @StringRes descriptionRes: Int = R.string.notification_simple_description,
    ): Int {
        val pendingIntent = createPendingIntent()

        // channel given to builder is ignored on Android 7.1 and older
        val builder = NotificationCompat.Builder(
            context,
            DEFAULT_CHANNEL_ID,
        )
            .setSmallIcon(icon)
            .setContentTitle(context.getString(titleRes))
            .setContentText(context.getString(descriptionRes))
            // only for android 7.1 and lower, in newer version the priority is set in the notification channel
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            // automatically removes the notification when the user taps it
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(currentNotificationCount++, builder.build())
        }
        return currentNotificationCount - 1
    }

    /**
     *  Create an explicit intent for an Activity in your app
     */
    private fun createPendingIntent(): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }
}