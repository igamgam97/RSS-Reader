package com.example.rssanimereader.model.dataSource.feedListDataSource

import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.dbAPI.FeedApi

class FeedListLocalDataSource(private val feedApi: FeedApi) :
        FeedListDataSource {

    override fun getFeedsByChannel(linkChannel: String, onDataReady: (ArrayList<FeedItem>) -> Unit) {

        feedApi.getFeedsByChannel(linkChannel) { data ->
            run {
                onDataReady(data)

            }
        }
    }

    override fun getAllFeeds(onDataReady: (ArrayList<FeedItem>) -> Unit) {
        feedApi.getAllFeeds { data ->
            run {
                onDataReady(data)
            }
        }
    }
}
