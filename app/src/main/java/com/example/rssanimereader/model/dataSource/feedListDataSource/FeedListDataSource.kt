package com.example.rssanimereader.model.dataSource.feedListDataSource

import com.example.rssanimereader.entity.FeedItem
import io.reactivex.Single

interface FeedListDataSource {
    fun getAllFeeds(): Single<ArrayList<FeedItem>>
    fun getFeedsByChannel(linkChannel: String) : Single<ArrayList<FeedItem>>
}