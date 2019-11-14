package com.example.android2

import android.app.job.JobInfo
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.PersistableBundle

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent!!.getIntExtra("id", -1)
        val title = intent.getStringExtra("title")!!

        val bundle = PersistableBundle().apply {
            putInt("id", id)
            putString("title", title)
        }

        val jobInfo = JobInfo.Builder(11, ComponentName(context!!, MyJobService::class.java))
            .setOverrideDeadline(0)
            .setExtras(bundle)
            .build()
        MainActivity.jobScheduler!!.schedule(jobInfo)
    }
}