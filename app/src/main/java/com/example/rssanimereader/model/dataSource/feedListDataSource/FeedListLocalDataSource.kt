package com.example.rssanimereader.model.dataSource.feedListDataSource

import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.dbAPI.ChannelAndFeedApi
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class FeedListLocalDataSource(private val feedApi: ChannelAndFeedApi) :
    FeedListDataSource {
    override fun getFeedsByChannel(linkChannel: String) = Single.fromCallable<ArrayList<FeedItem>> {
        feedApi.getFeedsByChannel(linkChannel)
    }.subscribeOn(Schedulers.io())

    override fun getAllFeeds() = Single.fromCallable{feedApi.getAllFeeds()}.subscribeOn(Schedulers.io())

    fun getFavoriteFeeds() = Single.fromCallable{feedApi.getFavoriteFeeds()}.subscribeOn(Schedulers.io())

    fun saveFeedsByChannel(data: Pair<ArrayList<FeedItem>, ChannelItem>) =
        Completable
            .fromCallable { feedApi.saveFeedsAndChannel(data) }
            .subscribeOn(Schedulers.io())

    fun getAllChannels(): Observable<String> =
        Observable
            .fromIterable(feedApi.getAllUrlChannels())
            .subscribeOn(Schedulers.io())


}

//todo допилить add channel fragment
//todo добавить кнопу удалить прочитанные