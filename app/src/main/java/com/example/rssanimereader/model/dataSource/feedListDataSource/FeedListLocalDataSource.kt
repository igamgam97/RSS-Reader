package com.example.rssanimereader.model.dataSource.feedListDataSource

import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.dbAPI.FeedApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class FeedListLocalDataSource(private val feedApi: FeedApi) :
    FeedListDataSource {


    override fun getAllFeeds(onDataReady: (ArrayList<FeedItem>) -> Unit) {
        feedApi.getAllFeeds { data ->
            run {
                onDataReady(data)
            }
        }
    }

    override fun getFeedsByChannel(linkChannel: String) = Single.fromCallable<ArrayList<FeedItem>> {
        feedApi.getFeedsByChannel(linkChannel)
    }.subscribeOn(Schedulers.io())

    fun getAllFeeds() = Single.fromCallable{feedApi.getAllFeeds()}.subscribeOn(Schedulers.io())
}
