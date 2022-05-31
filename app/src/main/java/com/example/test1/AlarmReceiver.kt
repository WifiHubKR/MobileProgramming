package com.example.test1

import android.R
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService


class AlarmReceiver : BroadcastReceiver() {
    var manager: NotificationManager? = null
    var builder: NotificationCompat.Builder? = null

    override fun onReceive(context: Context, intent: Intent) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelID = "MyChannelWeek"
            val channelName = "week"
            val notificationChannel = NotificationChannel(channelID, channelName,
                NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE


            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)

            val builder = NotificationCompat.Builder(context, channelID)
                .setContentTitle("술약속이 있으신가요?")
                .setContentText("접속하셔서 일정을 기록하고 목표를 달성해보세요!")
                .setSmallIcon(com.example.test1.R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            manager.createNotificationChannel(notificationChannel)

            val notification = builder.build()
            manager.notify(10, notification)
            Log.v("alarm", "Week 알람 발생")
        }


    }
}