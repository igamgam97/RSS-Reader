package com.example.rssanimereader.util.feedUtil

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.dbAPI.DatabaseAPI
import java.io.InputStream
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class RemoteDataSaver<T>(
    private val urlPath: String,
    private val remoteDataParser: RemoteDataParser,
    private val saveRemoteDataInterface: DatabaseAPI
) {

    fun validateData() {
        val (data, channel) = getData()
        Log.d("bag",channel.nameChannel)
        saveRemoteDataInterface.open().use {
            if(!it.isExistChannel(urlPath)) {
                channel.image =      downloadImage(channel.pathImage)
                it.insertChannel(channel)
            }
            it.insertAll(data)
        }

    }

    @Throws(MalformedURLException::class)
    private fun getData(): Pair<ArrayList<FeedItem>,ChannelItem> {

        //todo add more check exception
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
                return remoteDataParser.parse(httpConnection.inputStream)
            } else throw ConnectException("Error connection")
        } finally {
            httpConnection.disconnect()
        }

    }

    private fun downloadImage(urlPath:String): Bitmap {
        val url = URL(urlPath)
        val httpConnection = (url.openConnection() as HttpURLConnection).apply {
            connectTimeout = FeedUtilConstants.CONNECT_TIMEOUT_VALUE
            readTimeout = FeedUtilConstants.READ_TIMEOUT_VALUE
            requestMethod = "GET"
            doInput = true
        }

        httpConnection.connect()
        val input=httpConnection.inputStream
        return BitmapFactory.decodeStream(input)

    }

}

/*interface SaveRemoteDataInterface<T> {
    fun insertAll(items: List<T>)

    fun open(): SaveRemoteDataInterface<T>

    fun close()
}*/

interface RemoteDataParser {
    fun parse(input: InputStream): Pair<ArrayList<FeedItem>, ChannelItem>
}