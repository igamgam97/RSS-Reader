package com.example.rssanimereader.model.repository

import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.model.WebDS
import com.example.rssanimereader.model.dataSource.LocalDS
import io.reactivex.Completable
import io.reactivex.Observable

class FeedsRepository(
    private val webDS: WebDS,
    private val localDS: LocalDS
){
    fun setFavoriteFeed(feed: FeedItem): Completable = localDS.setFavoriteFeed(feed)
    fun setIsRead(feed: FeedItem): Completable = localDS.setIsRead(feed)
    fun getFeedsByChannelFromDB(linkChannel: String) = localDS.getFeedsByChannelFromDB(linkChannel)

    fun getFeedsFromCashe(linkChannel: String) = when (linkChannel) {
        "" -> localDS.getAllFeeds()
        "favorite" -> localDS.getFavoriteFeeds()
        else -> localDS.getFeedsByChannel(linkChannel)
    }

    fun getChannelsFromDB(linkChannel: String): Observable<String> =
        when (linkChannel) {
            "" -> localDS.getChannelsLink()
            else -> Observable.fromIterable(arrayListOf(linkChannel))
        }


}