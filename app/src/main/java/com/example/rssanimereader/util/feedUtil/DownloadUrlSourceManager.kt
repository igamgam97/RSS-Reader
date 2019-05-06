package com.example.rssanimereader.util.feedUtil

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent

class DownloadUrlSourceManager(private val context: Context) {

    lateinit var broadcastReceiver: BroadcastReceiver

    fun getData(path: String, onDataReady: () -> Unit) {

        val intent = Intent(context, RSSDownloadService::class.java)
        intent.putExtra(FeedUtilConstants.URL, path)
        context.startService(intent)
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                //todo check this part on logic
                onDataReady()
            }

        }
        val intentFilter = IntentFilter(FeedUtilConstants.BROADCAST_STATE_DATA_ACTION)
        context.registerReceiver(broadcastReceiver, intentFilter)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun disconnetct(){
        context.unregisterReceiver(broadcastReceiver)
    }

}

