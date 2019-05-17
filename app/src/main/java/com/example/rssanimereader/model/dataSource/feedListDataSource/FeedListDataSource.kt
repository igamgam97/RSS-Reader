package com.example.rssanimereader.model.dataSource.feedListDataSource

import com.example.rssanimereader.entity.FeedItem
import io.reactivex.Single

interface FeedListDataSource {
    fun getAllFeeds(onDataReady: (ArrayList<FeedItem>) -> Unit)
    fun getFeedsByChannel(linkChannel: String) : Single<ArrayList<FeedItem>>
}