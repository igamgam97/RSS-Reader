package com.example.rssanimereader.util.feedUtil

import android.app.IntentService
import android.content.Intent
import com.example.rssanimereader.di.Injection


class RSSDownloadService : IntentService("RSSDownloadService") {


    override fun onHandleIntent(intent: Intent) {
        val urlPath = intent.getStringExtra(FeedUtilConstants.URL)

        val remoteDataSaver = Injection.provideRemoteDataSaver(this, urlPath)

        remoteDataSaver {
            isDataPublishedSuccessful()
        }


    }


    private fun isDataPublishedSuccessful() {
        val intent = Intent(FeedUtilConstants.BROADCAST_STATE_DATA_ACTION)
        /*intent.putExtra(FeedUtilConstants.STATUS_DATA, statusData)*/
        sendBroadcast(intent)
    }
}
