package com.example.rssanimereader.util.dbAPI

import com.example.rssanimereader.entity.FeedItem


class FeedApi(private val dataBaseConnection: DatabaseAPI) {


    fun getFeedsByChannel(linkChannel: String):ArrayList<FeedItem>
            = dataBaseConnection.getFeedsByChannel((linkChannel))

    fun getAllFeeds()
            = dataBaseConnection.getItemFeeds()

    fun getFavoriteFeeds():ArrayList<FeedItem>
            = dataBaseConnection.getFavoriteFeeds()


    fun setFavorite(feed: FeedItem) {
        dataBaseConnection.setFavoriteFeed(feed)
    }

    fun setisRead(feed: FeedItem) {
        dataBaseConnection.setIsReadFeed(feed).toString()
    }


}


