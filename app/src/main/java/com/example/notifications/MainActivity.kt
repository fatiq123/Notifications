package com.example.notifications

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    val CHANNEL_ID = "channelID"
    val CHANNEL_NAME = "channelName"
    val NOTIFICATION_ID = 0

    private lateinit var btnNotification: Button
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()


        btnNotification = findViewById(R.id.btnShowNotification)

//        // if the notification comes then if app close then if we press on notification then the app will open
//        val intent = Intent(this,MainActivity::class.java)
//        val pendingIntent = TaskStackBuilder.create(this).run {
//            addNextIntentWithParentStack(intent)
//            getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT)
//        }

        val requestCode = 0 // Provide a unique request code if using multiple PendingIntents
        val flags = PendingIntent.FLAG_UPDATE_CURRENT // Specify any flags you need
        val pendingIntent = PendingIntent.getActivity(this, requestCode, intent, flags)



        val notification = NotificationCompat.Builder(this,CHANNEL_ID)
            .setContentTitle("Awesome Notification")
            .setContentText("This is the content text")
            .setSmallIcon(R.drawable.ic_star)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setContentIntent(pendingIntent)    // in order to run app from notification
            .build()



        val notificationManager = NotificationManagerCompat.from(this)

        btnNotification.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    0
                )
                return@setOnClickListener // Return here to handle permission request
            }

            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }


    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                lightColor = Color.GREEN
                enableLights(true)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

}