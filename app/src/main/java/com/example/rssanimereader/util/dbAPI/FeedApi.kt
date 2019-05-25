package com.example.rssanimereader.util.dbAPI

import com.example.rssanimereader.entity.FeedItem


class FeedApi(private val dataBaseConnection: DatabaseAPI) {


    fun getFeedsByChannel(linkChannel: String) = dataBaseConnection.getFeedsByChannel((linkChannel))

    fun getAllFeeds() = dataBaseConnection.getItemFeeds()

    fun setFavorite(feed: FeedItem) {
        dataBaseConnection.updateFeed(feed)
    }


}


