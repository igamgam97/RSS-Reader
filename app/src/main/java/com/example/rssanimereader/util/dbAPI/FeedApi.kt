package com.example.rssanimereader.util.dbAPI

import com.example.rssanimereader.entity.FeedItem


class FeedApi(private val dataBaseConnection: DatabaseAPI) {

    fun getFeedsByChannel(linkChannel: String): ArrayList<FeedItem> =
        dataBaseConnection.getFeedsByChannel((linkChannel))

    fun getAllFeeds() = dataBaseConnection.getAllFeeds()

    fun getFavoriteFeeds(): ArrayList<FeedItem> = dataBaseConnection.getFavoriteFeeds()

    fun setFavoriteFeed(feed: FeedItem) {
        dataBaseConnection.setFavoriteFeed(feed)
    }

    fun setIsReadFeed(feed: FeedItem) {
        dataBaseConnection.setIsReadFeed(feed)
    }
}


