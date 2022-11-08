package com.example.stopwatchapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stopwatchapp.databinding.ActivityMainBinding
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var serviceintent:Intent
    private var isstarted=false
    private var  time =0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.start.setOnClickListener()
        {
            startorstop()
        }
        binding.reset.setOnClickListener()
        {
            reset()
        }
        serviceintent= Intent(applicationContext,stopwatchservice::class.java)
        registerReceiver(updatetime, IntentFilter(stopwatchservice.updatedtime))

    }


    private fun startorstop()
    {
        if(isstarted)
        {
            stop()
        }
        else
        {
            start()
        }
    }
    private fun start(){
        serviceintent.putExtra(stopwatchservice.currenttime,time)
        startService(serviceintent)
        binding.start.text="STOP"
        isstarted=true

    }

    private fun stop(){
        stopService(serviceintent)
        binding.start.text="START"
        isstarted=false

    }

    private fun reset() {
        stop()
        time=0.0
        binding.time.text=getformattedtime(time)

    }
    private var updatetime :BroadcastReceiver= object: BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(stopwatchservice.currenttime,0.0)
            binding.time.text=getformattedtime(time)
        }

    }
    private fun getformattedtime(time : Double):String{
        val timeint = time.roundToInt()
        var hrs = timeint % 86400 /3600
        var min = timeint % 86400 % 3600 / 60
        var sec = timeint % 86400 % 3600 % 60
        return String.format("%02d:%02d:%02d",hrs,min,sec)
    }
}