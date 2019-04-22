package com.example.rssanimereader

import android.app.IntentService
import android.content.Intent
import com.example.rssanimereader.data.FeedItem
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL


class RSSDownloadService : IntentService("RSSDownloadService") {

    private val url = "urlpath"

    private val TIMEOUT_VALUE = 10000


    override fun onHandleIntent(intent: Intent?) {
        var inputStream: InputStream
        val urlPath = intent?.getStringExtra(url)
        val url = URL(urlPath)
        val httpConnection = url.openConnection() as HttpURLConnection
        try {
            httpConnection.readTimeout = TIMEOUT_VALUE
            httpConnection.connectTimeout = TIMEOUT_VALUE
            httpConnection.requestMethod = "GET"
            httpConnection.doInput = true

            httpConnection.connect()

            val responseCode = httpConnection.responseCode

            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpConnection.inputStream
            }
        } catch (ex: Exception) {

            throw IOException("Error connection")
        }

    }

    private fun publishResults(data: ArrayList<FeedItem>) {
        val intent = Intent("Notification")
        intent.putExtra("Data", data)
        sendBroadcast(intent)
    }
}
