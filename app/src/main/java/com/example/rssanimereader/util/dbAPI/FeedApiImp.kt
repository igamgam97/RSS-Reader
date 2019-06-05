package com.example.rssanimereader.util.dbAPI

import com.example.rssanimereader.entity.FeedItem

interface FeedApiImp {
    fun getFeedsByChannel(linkChannel: String): ArrayList<FeedItem>

    fun getAllFeeds(): ArrayList<FeedItem>

    fun getFavoriteFeeds(): ArrayList<FeedItem>

    fun setFavorite(feed: FeedItem)

    fun setisRead(feed: FeedItem)
}