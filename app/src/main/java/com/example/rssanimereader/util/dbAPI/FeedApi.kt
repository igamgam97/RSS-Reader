package com.example.rssanimereader.util.dbAPI

import android.content.Context
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.TaskInOtherThread


class FeedApi(private val context: Context) {

    val taskInOtherThread: TaskInOtherThread = TaskInOtherThread()

    fun getAllFeeds(onDataReady: (ArrayList<FeedItem>) -> Unit) {

        taskInOtherThread {
            DatabaseAPI(context).open().use {
                onDataReady(it.getItemFeeds() as ArrayList<FeedItem>)
            }
        }

    }


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
}


