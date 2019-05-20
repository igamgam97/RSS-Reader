package com.example.rssanimereader.service

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
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
                val path = ImageSaver.saveImageToInternalStorage(channel.urlImage, channel.nameChannel)
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




}

interface RemoteDataParser {
    fun parse(input: InputStream): Pair<ArrayList<FeedItem>, ChannelItem>
}