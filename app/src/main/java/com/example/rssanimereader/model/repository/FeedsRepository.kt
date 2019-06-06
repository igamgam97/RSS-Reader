package com.example.rssanimereader.model.repository

import com.example.rssanimereader.domain.entity.FeedItem
import com.example.rssanimereader.model.dataSource.localDS.LocalDS
import com.example.rssanimereader.model.dataSource.webDS.WebDS
import com.example.rssanimereader.model.repository.contracts.FeedsRepositoryContract
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class FeedsRepository(
    private val webDS: WebDS,
    private val localDS: LocalDS
) : FeedsRepositoryContract {
    override fun setFavoriteFeed(feed: FeedItem): Completable =
        localDS.setFavoriteFeed(feed)
    override fun setIsRead(feed: FeedItem): Completable
            = localDS.setIsRead(feed)
    override fun getFeedsByChannelFromDB(linkChannel: String) : Single<ArrayList<FeedItem>>
            = localDS.getFeedsByChannelFromDB(linkChannel)

    override fun getFeedsFromCashe(linkChannel: String): Single<ArrayList<FeedItem>> =
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