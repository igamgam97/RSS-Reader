package com.example.rssanimereader.service

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.ImageSaver
import com.example.rssanimereader.util.StreamFromURLLoader
import com.example.rssanimereader.util.dbAPI.DatabaseAPI
import io.reactivex.Completable
import java.io.InputStream
import java.net.MalformedURLException

class RemoteDataSaver<T>(
    private val remoteDataParser: RemoteDataParser,
    private val saveRemoteDataInterface: DatabaseAPI
) {


    fun saveDataFromApi(urlPath: String) = Completable.fromCallable { saveData(urlPath) }

    private fun saveData(urlPath: String) {
        val (feeds, channel) = getFeedsAndChannel(urlPath)
        saveRemoteDataInterface.open().use {
            if (!it.isExistChannel(urlPath)) {
                val image = downloadImage(channel.urlImage)
                val path = ImageSaver.saveImageToInternalStorage(image, channel.nameChannel)
                channel.pathImage = path.toString()
                it.insertChannel(channel)
            }

            it.insertAll(feeds)

        }

    }

    @Throws(MalformedURLException::class)
    private fun getFeedsAndChannel(urlPath: String): Pair<ArrayList<FeedItem>, ChannelItem> {

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

interface RemoteDataParser {
    fun parse(input: InputStream): Pair<ArrayList<FeedItem>, ChannelItem>
}