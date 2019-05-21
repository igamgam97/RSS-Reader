package com.example.rssanimereader.util.dbAPI

import android.content.Context
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.TaskInOtherThread


class FeedApi(private val context: Context) {


    fun getFeedsByChannel(linkChannel: String): ArrayList<FeedItem> {
        var feeds = ArrayList<FeedItem>()
        DatabaseAPI(context).open().use {
            feeds = it.getFeedsByChannel(linkChannel)
        }
        return feeds
    }

    fun getAllFeeds(): ArrayList<FeedItem> {
        var feeds = ArrayList<FeedItem>()
        DatabaseAPI(context).open().use {
            feeds = it.getItemFeeds()
        }
        return feeds
    }

    fun setFavorite(feed:FeedItem){
        DatabaseAPI(context).open().use {
            it.updateFeed(feed)
        }
    }


}


