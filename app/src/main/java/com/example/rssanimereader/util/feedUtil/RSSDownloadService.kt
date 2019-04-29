package com.example.rssanimereader.util.feedUtil

import android.app.IntentService
import android.content.Intent
import com.example.rssanimereader.util.dbAPI.DatabaseAPI
import com.example.rssanimereader.util.feedUtil.parser.RSSRemoteDataParser


class RSSDownloadService : IntentService("RSSDownloadService") {


    override fun onHandleIntent(intent: Intent) {
        val urlPath = intent.getStringExtra(FeedUtilConstants.URL)

        val dbAPI = RomoteDataSaver(urlPath, RSSRemoteDataParser(urlPath), DatabaseAPI(this))

        dbAPI.validateData()
        isDataPublishedSuccessful(true)

    }


    private fun isDataPublishedSuccessful(statusData: Boolean) {
        val intent = Intent(FeedUtilConstants.BROADCAST_STATE_DATA_ACTION)
        intent.putExtra(FeedUtilConstants.STATUS_DATA, statusData)
        sendBroadcast(intent)
    }
}
