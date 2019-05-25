package com.example.rssanimereader.util.dbAPI

import com.example.rssanimereader.entity.ChannelItem

class ChannelAPI(
    val dataBaseConnection: DatabaseAPI
) {


    fun getChannels() = dataBaseConnection.getAllChannels()

    fun deleteChannel(channelName: String): ArrayList<ChannelItem> {
        dataBaseConnection.deleteFeedsByChannel(channelName)
        dataBaseConnection.deleteChannel(channelName)
        return dataBaseConnection.getAllChannels()
    }

    companion object {
        const val FAVORITE_CHANNELS = "Favorite_Channels"
        const val KEY_CHANNEL = "KEY_CHANNEL"
    }

}