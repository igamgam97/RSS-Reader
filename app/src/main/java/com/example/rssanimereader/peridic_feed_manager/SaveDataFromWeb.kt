package com.example.rssanimereader.peridic_feed_manager

import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.web.RemoteDataParser
import com.example.rssanimereader.web.ImageSaver
import com.example.rssanimereader.web.StreamFromURLLoader
import com.example.rssanimereader.util.dbAPI.ChannelAPI
import com.example.rssanimereader.util.dbAPI.DatabaseAPI
import io.reactivex.Completable

class SaveDataFromWeb(
    private val remoteDataParser: RemoteDataParser,
    private val dataBase: DatabaseAPI
) {

    fun downloadAndSaveAllFeedsApi()
            = Completable.fromCallable {downloadAndSaveAllFeeds()}

    fun downloadAndSaveAllFeeds () {
        val channelList = ChannelAPI(dataBase).getUrlChannels()
        if (channelList.isNotEmpty()){
            channelList.forEach(::saveData)
        }
    }

    private fun saveData(urlPath: String) {
        val (feeds, channel) = getFeedsAndChannel(urlPath)
        if (!dataBase.isExistChannel((urlPath))) {
            val path = ImageSaver.saveImageToInternalStorage(channel.urlImage, channel.nameChannel)
            channel.pathImage = path.toString()
            dataBase.insertChannel(channel)
        }

        dataBase.insertAllFeedsByChannel(feeds,urlPath)


    }

    private fun saveDataApi(urlPath: String,data:Pair<ArrayList<FeedItem>, ChannelItem>) {
        val (feeds, channel) = data
        if (!dataBase.isExistChannel((urlPath))) {
            val path = ImageSaver.saveImageToInternalStorage(channel.urlImage, channel.nameChannel)
            channel.pathImage = path.toString()
            dataBase.insertChannel(channel)
        }

        dataBase.insertAllFeedsByChannel(feeds,urlPath)


    }

   /* private fun getFeedsAndChannelApi(urlPath: String) =
        Observable
            .fromIterable( ChannelAPI(dataBase).getUrlChannels())
            .flatMapSingle { urlPath ->  Single.fromCallable{getFeedsAndChannelFromWeb(urlPath)}}
        Single.fromCallable{getFeedsAndChannelFromWeb(urlPath)}
            .flatMapCompletable { data -> Completable.fromCallable { saveFeedsAndChannel(urlPath, data) } }*/

    private fun getFeedsAndChannel(urlPath: String): Pair<ArrayList<FeedItem>, ChannelItem> {

        val streamFromURLLoader = StreamFromURLLoader()

        streamFromURLLoader(urlPath).inputStream.use {
            return remoteDataParser.parse(it,urlPath)
        }
    }


}