package com.example.rssanimereader.util.feedUtil

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import java.io.Closeable

class DownloadUrlSourceManager(private val context: Context)  {

    lateinit var broadcastReceiver: BroadcastReceiver

    fun getData(path: String, onDataReady: () -> Unit) {

        startService(path)

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                onDataReady()
            }

        }
       onConnect()
    }

    fun onDisconnect(){
        context.unregisterReceiver(broadcastReceiver)
    }

    fun onConnect(){
        val intentFilter = IntentFilter(FeedUtilConstants.BROADCAST_STATE_DATA_ACTION)
        context.registerReceiver(broadcastReceiver, intentFilter)
    }

    private fun startService(path: String){
        val intent = Intent(context, RSSDownloadService::class.java)
        intent.putExtra(FeedUtilConstants.URL, path)
        context.startService(intent)
    }

}

