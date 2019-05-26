package com.example.rssanimereader.util.dbAPI

import android.util.Log
import com.example.rssanimereader.entity.FeedItem


class FeedApi(private val dataBaseConnection: DatabaseAPI) {


    fun getFeedsByChannel(linkChannel: String) = dataBaseConnection.getFeedsByChannel((linkChannel))

    fun getAllFeeds() = dataBaseConnection.getItemFeeds()

    fun setFavorite(feed: FeedItem) {
        Log.d("bag","there")
        Log.d("bag",dataBaseConnection.updateFeed(feed).toString())
    }


}


