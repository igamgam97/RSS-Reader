package com.example.rssanimereader.util

import com.example.rssanimereader.util.feedUtil.FeedUtilConstants
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class StreamFromURLLoader  {

    var httpConnection:HttpURLConnection? = null

    @Throws(MalformedURLException::class)
    operator fun invoke(path: String): HttpURLConnection {
        val url = URL(path)
        val httpConnection = (url.openConnection() as HttpURLConnection).apply {
            connectTimeout = FeedUtilConstants.CONNECT_TIMEOUT_VALUE
            readTimeout = FeedUtilConstants.READ_TIMEOUT_VALUE
            requestMethod = "GET"
            doInput = true
        }

        httpConnection.connect()
        val responseCode = httpConnection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return httpConnection
        } else throw ConnectException("Error connection")
    }

}

fun HttpURLConnection.use(onDataReady: HttpURLConnection.()->Unit){
    this.disconnect()
}