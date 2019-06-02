package com.example.rssanimereader.model.dataSource

import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.dbAPI.FeedApi
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class FeedDataSource(private val feedApi: FeedApi) {

    fun setFavorite(feed:FeedItem) : Completable {
        return Completable.fromCallable {feedApi.setFavorite(feed)}.subscribeOn(Schedulers.io())
    }

    fun setIsRead(feed:FeedItem) : Completable {
        return Completable.fromCallable {feedApi.setisRead(feed)}.subscribeOn(Schedulers.io())
    }
}