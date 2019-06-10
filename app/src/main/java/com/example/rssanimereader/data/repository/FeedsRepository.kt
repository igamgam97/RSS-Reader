package com.example.rssanimereader.data.repository

import com.example.rssanimereader.data.dataSource.contracts.ILocalDS
import com.example.rssanimereader.data.dataSource.contracts.IWebDS
import com.example.rssanimereader.data.repository.contracts.IFeedsRepository
import com.example.rssanimereader.domain.entity.FeedItem
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class FeedsRepository(
    private val webDS: IWebDS,
    private val localDS: ILocalDS
) : IFeedsRepository {
    override fun setFavoriteFeed(feed: FeedItem): Completable =
        localDS.setFavoriteFeed(feed)

    override fun setIsRead(feed: FeedItem): Completable = localDS.setIsRead(feed)
    override fun getFeedsByChannelFromDB(linkChannel: String): Single<ArrayList<FeedItem>> =
        localDS.getFeedsByChannelFromDB(linkChannel)

    override fun getFeedsFromCache(linkChannel: String): Single<ArrayList<FeedItem>> =
        when (linkChannel) {
            "" -> localDS.getAllFeeds()
            "favorite" -> localDS.getFavoriteFeeds()
            else -> localDS.getFeedsByChannel(linkChannel)
        }

    override fun getChannelsLinkFromDB(linkChannel: String): Observable<String> =
        when (linkChannel) {
            "" -> localDS.getChannelsLink()
            else -> Observable.fromIterable(arrayListOf(linkChannel))
        }


}