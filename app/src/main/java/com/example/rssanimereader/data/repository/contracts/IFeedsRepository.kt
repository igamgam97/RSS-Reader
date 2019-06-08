package com.example.rssanimereader.data.repository.contracts

import com.example.rssanimereader.domain.entity.FeedItem
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IFeedsRepository {
    fun setFavoriteFeed(feed: FeedItem): Completable
    fun setIsRead(feed: FeedItem): Completable
    fun getFeedsByChannelFromDB(linkChannel: String): Single<ArrayList<FeedItem>>
    fun getFeedsFromCashe(linkChannel: String): Single<ArrayList<FeedItem>>
    fun getChannelsLinkFromDB(linkChannel: String): Observable<String>
}