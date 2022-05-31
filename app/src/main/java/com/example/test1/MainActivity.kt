package com.example.test1

import android.app.*
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.test1.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var DBHelper: DBHelper
    lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initDB()
    }


    fun initDB() {
        val dbfile = getDatabasePath("mydb.db")
        //데이터베이스 폴더가 존재하지 않는 경우 실행하는 함수
        if (!dbfile.parentFile.exists()) {
            dbfile.parentFile.mkdir()
        }
    }

    private fun init() {
        val alarmManagerWeek = getSystemService(ALARM_SERVICE) as AlarmManager
        val receiverIntent = Intent(this@MainActivity, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this@MainActivity,
            0,
            receiverIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        alarmManagerWeek.cancel(pendingIntent)



        binding.setting.setOnClickListener {

            val layoutInflater = LayoutInflater.from(this)
            val view = layoutInflater.inflate(R.layout.object_dialog, null)


            val alertDialog = AlertDialog.Builder(this)
                .setView(view)
                .create()

            val pButton = view.findViewById<Button>(R.id.button)
            val nButton = view.findViewById<Button>(R.id.button2)
            val dayObject = view.findViewById<TextView>(R.id.day_object)
            val weekObject = view.findViewById<TextView>(R.id.week_object)


            pButton.setOnClickListener {
                if (dayObject.text != null && weekObject.text != null) {
                    val daily_goal = dayObject.text.toString().toInt()
                    val weekly_goal = weekObject.text.toString().toInt()

                } else {
                    Toast.makeText(this, "목표를 모두 입력하셔야 합니다.", Toast.LENGTH_SHORT).show()
                }
                alertDialog.dismiss()
            }

            nButton.setOnClickListener {
                alertDialog.dismiss()
            }

            alertDialog.show()
        }

    }




    @RequiresApi(Build.VERSION_CODES.N)
    override fun onDestroy() {
        super.onDestroy()
        setWeekAlarm()

    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setDayAlarm() {
        val alarmManagerDay = getSystemService(ALARM_SERVICE) as AlarmManager
        val receiverIntent = Intent(this@MainActivity, DayReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this@MainActivity,
            0,
            receiverIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )


        val calendar = java.util.Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 0)

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManagerDay.setWindow(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        } else {
            alarmManagerDay.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }
    }

    fun setWeekAlarm() {
        val alarmManagerWeek = getSystemService(ALARM_SERVICE) as AlarmManager
        val receiverIntent = Intent(this@MainActivity, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this@MainActivity,
            0,
            receiverIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        val calendar = java.util.Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManagerWeek.setWindow(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis + 86400000 * 7,
                86400000 * 7,
                pendingIntent
            )
        } else {
            alarmManagerWeek.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis + 86400000 * 7,
                86400000 * 7,
                pendingIntent
            )
        }
    }
}
