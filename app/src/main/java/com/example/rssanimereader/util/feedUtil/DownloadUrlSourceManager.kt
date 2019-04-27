package com.example.rssanimereader.util.feedUtil

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.dbAPI.DatabaseAPI

class DownloadUrlSourceManager(private val context: Context) {

    fun getData(path: String, onRepositoryReadyCallback: onDownloadUrlSourceManagerCallback) {
        val intent = Intent(context, RSSDownloadService::class.java)
        intent.putExtra(FeedUtilConstants.URL, path)
        context.startService(intent)
        val br = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val arrayList = ArrayList<FeedItem>()
                arrayList.add(
                    FeedItem(
                        "First",
                        "Owner 1",
                        "link 1",
                        "Sat, 20 Apr 2019 17:55:23 GMT",
                        "b"
                    )
                )
                arrayList.add(
                    FeedItem(
                        "Second",
                        "Owner 2",
                        "link 2",
                        "Sat, 20 Apr 2019 17:55:23 GMT",
                        "d"
                    )
                )
                arrayList.add(
                    FeedItem(
                        "Third",
                        "Owner 3",
                        "link 3",
                        "Sat, 20 Apr 2019 17:55:23 GMT",
                        "df"
                    )
                )
                val dataBaseAPI = DatabaseAPI(context!!).open()
                val feeds = dataBaseAPI.getItemFeeds()
                dataBaseAPI.close()
                onRepositoryReadyCallback.onDataReady(arrayList)
                Log.d("bag",feeds.size.toString())
            }

        }
        val intentFilter = IntentFilter(FeedUtilConstants.BROADCAST_STATE_DATA_ACTION)
        context.registerReceiver(br,intentFilter)
    }

}

interface onDownloadUrlSourceManagerCallback{
    fun onDataReady(data: ArrayList<FeedItem>)
}
