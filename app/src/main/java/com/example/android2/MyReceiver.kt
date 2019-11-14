package com.example.android2

import android.app.job.JobInfo
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.PersistableBundle
import android.util.Log

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("XXX", "Intent received")
        val id = intent!!.getIntExtra("id",-1)
        val title = intent.getStringExtra("title")!!
        Log.d("XXX", id.toString())
        Log.d("XXX", title)

        val bundle = PersistableBundle()
        bundle.putInt("id",id)
        bundle.putString("title",title)

        val jobInfo = JobInfo.Builder(11, ComponentName(context!!, MyJobService::class.java))
            .setOverrideDeadline(0)
            .setExtras(bundle)
            .build()
        MainActivity.jobScheduler!!.schedule(jobInfo)
    }
}