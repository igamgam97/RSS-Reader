package com.example.rssanimereader.service

import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.ImageSaver
import com.example.rssanimereader.util.StreamFromURLLoader
import com.example.rssanimereader.util.dbAPI.ChannelAPI
import com.example.rssanimereader.util.dbAPI.DatabaseAPI
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.InputStream

class RemoteDataSaver(
    private val remoteDataParser: RemoteDataParser,
    private val dataBase: DatabaseAPI
) {

    fun downloadAndSaveAllFeedsApi() = Completable.fromCallable { downloadAndSaveAllFeeds() }

    fun downloadAndSaveAllFeeds() {
        val channelList = ChannelAPI(dataBase).getUrlChannels()
        channelList.forEach(::saveData)
    }

    fun saveDataFromApi(urlPath: String) = Completable.fromCallable { saveData(urlPath) }

    private fun saveData(urlPath: String) {
        val (feeds, channel) = getFeedsAndChannel(urlPath)
        if (!dataBase.isExistChannel((urlPath))) {
            val path = ImageSaver.saveImageToInternalStorage(channel.urlImage, channel.nameChannel)
            channel.pathImage = path.toString()
            dataBase.insertChannel(channel)
        }

        dataBase.insertAllFeeds(feeds, urlPath)


    }

    fun getFeedsAndChannel(urlPath: String): Pair<ArrayList<FeedItem>, ChannelItem> {

        val streamFromURLLoader = StreamFromURLLoader()

        streamFromURLLoader(urlPath).inputStream.use {
            return remoteDataParser.parse(it, urlPath)
        }
    }


    fun getAllFeedsApi() = Observable
        .fromIterable(ChannelAPI(dataBase).getUrlChannels())
        .flatMapSingle { urlPath -> Single.fromCallable { getFeedsAndChannel(urlPath) } }
        .subscribeOn(Schedulers.io())

    fun saveDataApi(data: Pair<ArrayList<FeedItem>, ChannelItem>) {
        val (feeds, channel) = data
        if (!dataBase.isExistChannel((channel.linkChannel))) {
            val path = ImageSaver.saveImageToInternalStorage(channel.urlImage, channel.nameChannel)
            channel.pathImage = path.toString()
            dataBase.insertChannel(channel)
        }

        dataBase.insertAllFeeds(feeds, channel.linkChannel)


    }


}

interface RemoteDataParser {
    fun parse(input: InputStream, source: String): Pair<ArrayList<FeedItem>, ChannelItem>
}