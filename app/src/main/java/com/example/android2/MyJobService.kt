package com.example.android2

import android.app.Notification
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.ComponentName
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.android2.MainActivity.Companion.CHANNEL_ID
import kotlin.random.Random

class MyJobService : JobService() {
    override fun onStartJob(parameters: JobParameters?): Boolean {
        toast("Service started.")
        val id = parameters!!.extras.getInt("id")
        val title = parameters.extras.getString("title")!!
        with(NotificationManagerCompat.from(this)) {
            notify(Random.nextInt(), buildNotification(title,id))
        }
        return true
    }

    override fun onStopJob(parameters: JobParameters?): Boolean {
        toast("Service destroyed.")
        return false
    }

    private fun buildNotification(title:String,id:Int):Notification{
        val viewDetailsIntent = Intent().apply {
            action = "com.example.project1.ProductViewActivity"
            component = ComponentName("com.example.project1", "com.example.project1.ProductViewActivity")
            putExtra("productId",id)
        }
        val viewListIntent = Intent().apply {
            action = "com.example.project1.ProductListActivity"
            component = ComponentName("com.example.project1", "com.example.project1.ProductListActivity")
        }
        val viewDetailsPendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, viewDetailsIntent, 0)
        val viewListPendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, viewListIntent, 0)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(title)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(viewDetailsPendingIntent)
            .addAction(R.drawable.ic_launcher_foreground,getString(R.string.go_to_list),viewListPendingIntent)
            .setAutoCancel(true).build()
    }
}