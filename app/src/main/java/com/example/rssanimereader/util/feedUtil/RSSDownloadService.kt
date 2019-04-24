package com.example.rssanimereader.util.feedUtil

import android.app.IntentService
import android.content.Intent
import android.util.Log
import java.io.InputStream
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.URL


class RSSDownloadService : IntentService("RSSDownloadService") {


    lateinit var data: List<FeedItem>

    override fun onHandleIntent(intent: Intent) {
        val urlPath = intent.getStringExtra(FeedUtilConstants.URL)
        val streamData = onConnect(urlPath)
        data = Parser().parse(streamData)
        Log.d("bag", data.size.toString())
        isDataPublishedSuccessful(true)

    }

    private fun onConnect(urlPath: String): InputStream {

        val url = URL(urlPath)
        val httpConnection = (url.openConnection() as HttpURLConnection).apply {
            connectTimeout = FeedUtilConstants.CONNECT_TIMEOUT_VALUE
            readTimeout = FeedUtilConstants.READ_TIMEOUT_VALUE
            requestMethod = "GET"
            doInput = true
        }

        try {
            httpConnection.connect()
            val responseCode = httpConnection.responseCode
            // handle Internet Error later
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return httpConnection.inputStream
            } else throw ConnectException("Error connection")
        } catch (ex: ConnectException) {

            throw ConnectException("Error connection")
        }

    }

    private fun isDataPublishedSuccessful(statusData: Boolean) {
        val intent = Intent(FeedUtilConstants.BROADCAST_STATE_DATA_ACTION)
        intent.putExtra(FeedUtilConstants.STATUS_DATA, statusData)
        sendBroadcast(intent)
    }
}
