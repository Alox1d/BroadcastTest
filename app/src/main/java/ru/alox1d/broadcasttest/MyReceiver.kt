package ru.alox1d.broadcasttest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            ACTION_CLICKED -> Toast.makeText(
                context,
                "Clicked ${intent.getIntExtra(EXTRA_COUNT, 0)}",
                Toast.LENGTH_SHORT
            ).show()
            Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                val turnedOn = intent.getBooleanExtra("state", false)
                Toast.makeText(
                        context,
                "Airplane mode is changed. Turned on $turnedOn",
                Toast.LENGTH_SHORT
                ).show()
            }

            Intent.ACTION_BATTERY_LOW -> Toast.makeText(
                context,
                "Battery is low",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        const val ACTION_CLICKED = "clicked"
        const val EXTRA_COUNT = "count"
    }
}