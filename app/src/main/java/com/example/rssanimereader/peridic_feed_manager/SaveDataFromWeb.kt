package com.example.rssanimereader.peridic_feed_manager

import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.service.RemoteDataParser
import com.example.rssanimereader.util.ImageSaver
import com.example.rssanimereader.util.StreamFromURLLoader
import com.example.rssanimereader.util.dbAPI.ChannelAPI
import com.example.rssanimereader.util.dbAPI.DatabaseAPI
import io.reactivex.Completable
import java.io.InputStream
import java.net.MalformedURLException

class SaveDataFromWeb(
    private val remoteDataParser: RemoteDataParser,
    private val dataBase: DatabaseAPI
) {

    fun downloadAndSaveAllFeedsApi() = Completable.fromCallable {downloadAndSaveAllFeeds()}

    fun downloadAndSaveAllFeeds () {
        val channelList = ChannelAPI(dataBase).getUrlChannels()
        channelList.forEach(::saveData)
    }


    private fun saveData(urlPath: String) {
        val (feeds, channel) = getFeedsAndChannel(urlPath)
        if (!dataBase.isExistChannel((urlPath))) {
            val path = ImageSaver.saveImageToInternalStorage(channel.urlImage, channel.nameChannel)
            channel.pathImage = path.toString()
            dataBase.insertChannel(channel)
        }

        dataBase.insertAllFeeds(feeds,urlPath)


    }

    @Throws(MalformedURLException::class)
    private fun getFeedsAndChannel(urlPath: String): Pair<ArrayList<FeedItem>, ChannelItem> {

        val streamFromURLLoader = StreamFromURLLoader()

        streamFromURLLoader(urlPath).inputStream.use {
            return remoteDataParser.parse(it,urlPath)
        }
    }


}