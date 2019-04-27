package com.example.rssanimereader.util.feedUtil

import java.io.InputStream
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class RomoteDataSaver<T>(
    private val urlPath: String,
    private val remoteDataParser: RemoteDataParser<T>,
    private val saveRemoteDataInterface: SaveRemoteDataInterface<T>
) {

    fun validateData() {
        val data = getData()
        saveRemoteDataInterface.open()
        saveRemoteDataInterface.insertAll(data)
        saveRemoteDataInterface.close()
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
                return remoteDataParser.parse(httpConnection.inputStream)
            } else throw ConnectException("Error connection")
        } finally {
            httpConnection.disconnect()
        }

    }

}

interface SaveRemoteDataInterface<T> {
    fun insertAll(items: List<T>)

    fun open(): SaveRemoteDataInterface<T>

    fun close()
}

interface RemoteDataParser<T> {
    fun parse(input: InputStream): List<T>
}