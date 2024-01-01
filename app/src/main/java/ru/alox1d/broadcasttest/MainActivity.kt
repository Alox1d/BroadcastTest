package ru.alox1d.broadcasttest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {


    // private val receiver = MyReceiver()
    // TO communicate with View-elements
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                MyService.ACTION_LOADED -> {
                    val percent = intent.getIntExtra(MyService.EXTRA_PERCENT, 0)
                    progressBar.setProgress(percent, true)
                }
            }
        }
    }
    private var count = 0

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progress_bar)

        findViewById<Button>(R.id.button).setOnClickListener { sendCustomBroadcast() }
        registerBroadcast()
        startService()
    }

    private fun startService() {
        Intent(this, MyService::class.java).run {
            startService(this)
        }
    }

    private fun sendCustomBroadcast() {
        Intent(MyReceiver.ACTION_CLICKED).run {
            putExtra(MyReceiver.EXTRA_COUNT, ++count)
            sendBroadcast(this)
        }
    }

    private fun registerBroadcast() {
        val intentFilter = IntentFilter().apply {
            // addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            // addAction(Intent.ACTION_BATTERY_LOW)
            // addAction(MyReceiver.ACTION_CLICKED)
            addAction(MyService.ACTION_LOADED)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(receiver, intentFilter, RECEIVER_EXPORTED)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterReceiver(receiver)
    }
}