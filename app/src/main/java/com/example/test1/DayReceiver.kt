package com.example.test1

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.icu.util.TimeUnit
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import kotlin.time.DurationUnit

class DayReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelID = "MyChannelDay"
            val channelName = "Day"
            val notificationChannel = NotificationChannel(channelID, channelName,
                NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE


            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)

            val builder = NotificationCompat.Builder(context, channelID)
                .setContentTitle("하루 요약")
                .setContentText("오늘 하루 목표치 달성을 확인하시고 후기를 작성해주세요!")
                .setSmallIcon(com.example.test1.R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            manager.createNotificationChannel(notificationChannel)

            val notification = builder.build()
            manager.notify(5, notification)
            Log.v("alarm", "day 알람 발생")

        }
    }
}