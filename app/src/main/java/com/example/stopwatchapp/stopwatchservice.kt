package com.example.stopwatchapp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.Timer
import java.util.TimerTask

class stopwatchservice: Service() {

    private var timer=Timer()

    override fun onStartCommand(intent: Intent,tags: Int, startId: Int): Int {
        var time = intent.getDoubleExtra(currenttime,0.0)
        timer.scheduleAtFixedRate(stopwatchtimertask(time),0,1000)
     return START_NOT_STICKY
    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }


    override fun onBind(p0: Intent?): IBinder? = null

    companion object{
        const val currenttime = "current time"
        const val updatedtime = "updated time"
    }
    private inner class stopwatchtimertask(private var time: Double):TimerTask(){
        override fun run() {
            var intent=Intent(updatedtime)
            time++
            intent.putExtra(currenttime,time)
            sendBroadcast(intent)
        }

    }

}