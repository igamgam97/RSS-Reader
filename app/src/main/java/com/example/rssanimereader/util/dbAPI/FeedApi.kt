package com.example.rssanimereader.util.dbAPI

import android.content.Context
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.TaskInOtherThread


class FeedApi(private val context: Context) {

    val taskInOtherThread: TaskInOtherThread=TaskInOtherThread()

    fun getAllFeeds(onDataReady: (ArrayList<FeedItem>) -> Unit) {

        taskInOtherThread {
            DatabaseAPI(context).open().use {
                onDataReady(it.getItemFeeds() as ArrayList<FeedItem>)
            }
        }

    }

    fun getFeedsByChannel(linkChannel: String, onDataReady: (ArrayList<FeedItem>) -> Unit) {

        taskInOtherThread {
            DatabaseAPI(context).open().use {
                onDataReady(it.getFeedsByChannel(linkChannel))
            }

        }

    }

    fun deleteFeedsofChannel(channel: String) {
        taskInOtherThread {

            val dataBaseAPI=DatabaseAPI(context).open()
            dataBaseAPI.use {
                it.deleteFeedsByChannel(channel)
            }
        }
    }


}
