package com.example.rssanimereader.util.dbAPI

import com.example.rssanimereader.entity.ChannelItem

class ChannelAPI(
    private val dataBaseConnection: DatabaseAPI
) {


    fun getAllChannels(): ArrayList<ChannelItem> = dataBaseConnection.getAllChannels()

    /*fun deleteChannel(channelName: String): ArrayList<ChannelItem> {
        dataBaseConnection.deleteFeedsByChannel(channelName)
        dataBaseConnection.deleteChannel(channelName)
        return dataBaseConnection.getAllChannels()
    }*/

    fun deleteChannel(channelName: String){
        dataBaseConnection.deleteChannel(channelName)
    }

   /* fun deleteChannel(channelName: String): ArrayList<ChannelItem> {
        dataBaseConnection.deleteChannel(channelName)
        return dataBaseConnection.getAllChannels()
    }*/

    fun getUrlChannels(): ArrayList<String> =
        dataBaseConnection.getAllUrlChannels()

    fun insertChannel(channelItem: ChannelItem): Long =
        dataBaseConnection.insertChannel(channelItem)

}