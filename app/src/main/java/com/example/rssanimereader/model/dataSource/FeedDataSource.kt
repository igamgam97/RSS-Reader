package com.example.rssanimereader.model.dataSource

import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.dbAPI.ChannelAndFeedApi
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class FeedDataSource(private val feedApi: ChannelAndFeedApi) {

    fun setFavorite(feed: FeedItem): Completable =
        Completable.fromCallable { feedApi.setFavoriteFeed(feed) }.subscribeOn(Schedulers.io())

    fun setIsRead(feed: FeedItem): Completable =
        Completable.fromCallable { feedApi.setIsReadFeed(feed) }.subscribeOn(Schedulers.io())
}