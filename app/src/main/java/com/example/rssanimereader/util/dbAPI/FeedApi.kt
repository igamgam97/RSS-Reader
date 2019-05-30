package com.example.rssanimereader.util.dbAPI

import android.util.Log
import com.example.rssanimereader.entity.FeedItem


class FeedApi(private val dataBaseConnection: DatabaseAPI) {


    fun getFeedsByChannel(linkChannel: String) = dataBaseConnection.getFeedsByChannel((linkChannel))

    fun getAllFeeds() = dataBaseConnection.getItemFeeds()

    fun getFavoriteFeeds() = dataBaseConnection.getFavoriteFeeds()

    fun setFavorite(feed: FeedItem) {
        dataBaseConnection.updateFeed(feed).toString()
    }


}


