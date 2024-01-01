package ru.alox1d.broadcasttest

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlin.concurrent.thread



class MyService : Service() {

    // 3. Local Receiver
    private val localBroadcastManager by lazy {
        LocalBroadcastManager.getInstance(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        thread {
            for (i in 1..10000) {
                Log.d("MyService", "onStartCommand: $i")

                Thread.sleep(1000)
                Intent(ACTION_LOADED).run {
                    putExtra(EXTRA_PERCENT, i * 10)
                    localBroadcastManager.sendBroadcast(this)
                }
            }
        }

        return super.onStartCommand(intent, flags, startId) // = START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    companion object {
        const val ACTION_LOADED = "Loaded"
        const val EXTRA_PERCENT = "percent"
    }
}