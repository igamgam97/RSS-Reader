package com.example.rssanimereader.util.feedUtil

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

class DownloadUrlSourceManager(private val context: Context) {

    fun getData(path: String, onDataReady: () -> Unit) {

        val intent = Intent(context, RSSDownloadService::class.java)
        intent.putExtra(FeedUtilConstants.URL, path)
        context.startService(intent)
        val br = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                //todo check this part on logic
                onDataReady()
            }

        }
        val intentFilter = IntentFilter(FeedUtilConstants.BROADCAST_STATE_DATA_ACTION)
        context.registerReceiver(br, intentFilter)
    }

}

