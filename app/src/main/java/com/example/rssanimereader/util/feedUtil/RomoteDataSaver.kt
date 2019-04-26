package com.example.rssanimereader.util.feedUtil

import com.example.rssanimereader.util.feedUtil.parser.Parser
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class RomoteDataSaver<T>(
    private val urlPath: String,
    private val parser: Parser<T>,
    private val dbInsertAllFeeds: DBInsertAllFeeds<T>
) {

    fun validateData() {
        val data = getData()
        dbInsertAllFeeds.open()
        dbInsertAllFeeds.insertAll(data)
        dbInsertAllFeeds.close()
    }

    @Throws(MalformedURLException::class)
    private fun getData(): List<T> {
        val url = URL(urlPath)
        val httpConnection = (url.openConnection() as HttpURLConnection).apply {
            connectTimeout = FeedUtilConstants.CONNECT_TIMEOUT_VALUE
            readTimeout = FeedUtilConstants.READ_TIMEOUT_VALUE
            requestMethod = "GET"
            doInput = true
        }

        httpConnection.connect()
        val responseCode = httpConnection.responseCode
        try {
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return parser.parse(httpConnection.inputStream)
            } else throw ConnectException("Error connection")
        } finally {
            httpConnection.disconnect()
        }

    }

}
