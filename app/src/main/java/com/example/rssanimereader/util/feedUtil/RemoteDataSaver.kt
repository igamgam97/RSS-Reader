package com.example.rssanimereader.util.feedUtil

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.FeedAnalizator
import com.example.rssanimereader.util.ImageSaver
import com.example.rssanimereader.util.StreamFromURLLoader
import com.example.rssanimereader.util.dbAPI.DatabaseAPI
import com.example.rssanimereader.util.use
import java.io.InputStream
import java.net.MalformedURLException

class RemoteDataSaver<T>(
    private val urlPath: String,
    private val remoteDataParser: RemoteDataParser,
    private val saveRemoteDataInterface: DatabaseAPI
) {

    operator fun invoke(onDataReady: () -> Unit) {
        val (data, channel) = getData()

        saveRemoteDataInterface.open().use {
            if (!it.isExistChannel(urlPath)) {
                val image = downloadImage(channel.urlImage)
                val path = ImageSaver.saveImageToInternalStorage(image, channel.nameChannel)
                channel.pathImage = path.toString()
                it.insertChannel(channel)
            }

            it.insertAll(data)

        }
        onDataReady()

    }

    @Throws(MalformedURLException::class)
    private fun getData(): Pair<ArrayList<FeedItem>, ChannelItem> {

        val streamFromURLLoader = StreamFromURLLoader()

        streamFromURLLoader(urlPath).inputStream.use {
            return remoteDataParser.parse(it)
        }
    }

    @Throws(MalformedURLException::class)
    private fun downloadImage(urlPath: String): Bitmap {

        val streamFromURLLoader = StreamFromURLLoader()
        streamFromURLLoader(urlPath).inputStream.use {
            return BitmapFactory.decodeStream(it)
        }

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