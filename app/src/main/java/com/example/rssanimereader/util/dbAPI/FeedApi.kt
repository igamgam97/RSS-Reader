package com.example.rssanimereader.util.dbAPI

import android.util.Log
import com.example.rssanimereader.entity.FeedItem


class FeedApi(private val dataBaseConnection: DatabaseAPI) {


    fun getFeedsByChannel(linkChannel: String):ArrayList<FeedItem>{
        val data = dataBaseConnection.getFeedsByChannel((linkChannel))
        Log.d("bag",data.size.toString())
    return data
    }

    fun getAllFeeds() = dataBaseConnection.getItemFeeds()

    fun getFavoriteFeeds():ArrayList<FeedItem>{
        val data = dataBaseConnection.getFavoriteFeeds()
        data.forEach{Log.d("bag",it.itemFavorite.toString())}
        Log.d("bag",data.size.toString())
        return data
    }

    fun setFavorite(feed: FeedItem) {
        dataBaseConnection.updateFeed(feed).toString()
    }


}


