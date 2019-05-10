package com.example.rssanimereader.model.dataSource

import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.util.dbAPI.ChannelAPI

class ChannelListDataSource(private val channelApi: ChannelAPI) {
    fun getChannels(onDataReady: (ArrayList<ChannelItem>) -> Unit) {

        channelApi.getChannels(onDataReady)

    }

    fun deleteChannels(nameChannel:String,onDataReady: ( (ArrayList<ChannelItem>)) -> Unit){
        channelApi.deleteChannel(nameChannel,onDataReady)
    }
}