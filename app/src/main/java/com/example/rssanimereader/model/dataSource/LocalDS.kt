package com.example.rssanimereader.model.dataSource

import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.dbAPI.DatabaseAPI
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LocalDS(private val databaseAPI: DatabaseAPI) {
    fun setFavoriteFeed(feed: FeedItem): Completable =
        Completable.fromCallable { databaseAPI.setFavoriteFeed(feed) }.subscribeOn(Schedulers.io())

    fun setIsRead(feed: FeedItem): Completable =
        Completable.fromCallable { databaseAPI.setIsReadFeed(feed) }.subscribeOn(Schedulers.io())

    fun getChannels(): Single<ArrayList<ChannelItem>> =
        Single.fromCallable { databaseAPI.getAllChannels() }.subscribeOn(Schedulers.io())

    fun deleteChannels(nameChannel: String):Completable =
        Completable.fromCallable { databaseAPI.deleteChannel(nameChannel) }

    fun retractSaveChannel(channelItem: ChannelItem): Completable =
        Completable.fromCallable { databaseAPI.insertChannel(channelItem) }

    fun isExistChannel(channel: String): Boolean = databaseAPI.isExistChannel(channel)

    fun getFeedsByChannelFromDB(linkChannel: String) =
        Single
            .fromCallable { databaseAPI.getFeedsByChannel(linkChannel) }
            .subscribeOn(Schedulers.io())

    fun getChannelsLink(): Observable<String> =
        Observable
            .fromIterable(databaseAPI.getAllUrlChannels())
            .subscribeOn(Schedulers.io())

    fun saveFeedsByChannel(data: Pair<ArrayList<FeedItem>, ChannelItem>) =
        Completable
            .fromCallable { databaseAPI.saveFeedsAndChannel(data) }
            .subscribeOn(Schedulers.io())


    fun getFeedsByChannel(linkChannel: String) =
        Single
            .fromCallable<ArrayList<FeedItem>> { databaseAPI.getFeedsByChannel(linkChannel) }
            .subscribeOn(Schedulers.io())

    fun getAllFeeds() = Single.fromCallable{databaseAPI.getAllFeeds()}.subscribeOn(Schedulers.io())

    fun getFavoriteFeeds() = Single.fromCallable{databaseAPI.getFavoriteFeeds()}.subscribeOn(Schedulers.io())

}