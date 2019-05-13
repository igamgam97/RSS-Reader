package com.example.rssanimereader.util.dbAPI

import android.content.Context
import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.util.TaskInOtherThread

class ChannelAPI(
        val context: Context
) {


    val taskInOtherThread: TaskInOtherThread = TaskInOtherThread()


    fun getChannels(onDataReady: (ArrayList<ChannelItem>) -> Unit) {
        taskInOtherThread {
            DatabaseAPI(context).open().use {
                onDataReady(it.getAllChannels())
            }
        }
    }

    fun deleteChannel(channelName: String, onDataReady: (ArrayList<ChannelItem>) -> Unit) {
        taskInOtherThread {
            DatabaseAPI(context).open().use {
                it.deleteFeedsByChannel(channelName)
                it.deleteChannel(channelName)
                onDataReady(it.getAllChannels())
            }
        }
    }

    companion object {
        const val FAVORITE_CHANNELS = "Favorite_Channels"
        const val KEY_CHANNEL = "KEY_CHANNEL"
    }

}