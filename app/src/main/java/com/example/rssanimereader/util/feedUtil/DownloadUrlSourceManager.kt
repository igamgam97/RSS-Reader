package com.example.rssanimereader.util.feedUtil

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class DownloadUrlSourceManager(private val context: Context) {

    fun getData(path: String) {
        val intent = Intent(context, RSSDownloadService::class.java)
        intent.putExtra("urlpath", path)
        context.startService(intent)
        val br = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                TODO("add logic")
            }

        }
    }

}
