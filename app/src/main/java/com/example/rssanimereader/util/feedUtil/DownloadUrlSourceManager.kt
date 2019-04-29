package com.example.rssanimereader.util.feedUtil

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.example.rssanimereader.entity.FeedItem

class DownloadUrlSourceManager(private val context: Context) {

    fun getData(path: String, onDataReady: () -> Unit) {
        val intent = Intent(context, RSSDownloadService::class.java)
        intent.putExtra(FeedUtilConstants.URL, path)
        context.startService(intent)
        val br = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                /*
                val dataBaseAPI = DatabaseAPI(context!!).open()
                val feeds = dataBaseAPI.getItemFeeds()
                dataBaseAPI.close()
                Log.d("bag",feeds.size.toString())
                */
                //todo check this part on logic
                onDataReady()
            }

        }
        val intentFilter = IntentFilter(FeedUtilConstants.BROADCAST_STATE_DATA_ACTION)
        context.registerReceiver(br, intentFilter)
    }

}

interface onDownloadUrlSourceManagerCallback {
    fun onDataReady(data: ArrayList<FeedItem>)
}
