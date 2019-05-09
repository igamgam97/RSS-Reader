package com.example.rssanimereader.util.channelAPI

import android.content.Context
import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.util.TaskInOtherThread
import com.example.rssanimereader.util.dbAPI.DatabaseAPI

class ChannelSubscriptionsAPI(
    val context: Context
) {


    val taskInOtherThread: TaskInOtherThread=TaskInOtherThread()

    /* fun addChannel(targetChannel: String) {
         val listOfExistingChannels = getListChannel()
         val newSet = HashSet<String>()
         newSet.addAll(listOfExistingChannels)
         newSet.add(targetChannel)
         preferences
             .edit()
             .putStringSet(KEY_CHANNEL, newSet)
             .apply()
     }*/

    /*fun getListChannel(): MutableSet<String> {

        return preferences.getStringSet(KEY_CHANNEL, HashSet<String>()) ?: HashSet()
    }*/

    fun deleteChannel(channel: String) {

    }

    fun getChannels(onDataReady : (ArrayList<ChannelItem>) -> Unit) {
        taskInOtherThread {
            DatabaseAPI(context).open().use {
                onDataReady(it.getAllChannels())
            }
        }
    }

    fun deleteChannel(channelName:String,onDataReady : (ArrayList<ChannelItem>) -> Unit) {
        taskInOtherThread {
            DatabaseAPI(context).open().use {
                it.deleteFeedsByChannel(channelName)
                it.deleteChannel(channelName)
                onDataReady(it.getAllChannels())
            }
        }
    }

    companion object {
        const val FAVORITE_CHANNELS="Favorite_Channels"
        const val KEY_CHANNEL="KEY_CHANNEL"
    }

}