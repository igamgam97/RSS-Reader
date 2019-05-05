package com.example.rssanimereader.model.dataSource

import com.example.rssanimereader.entity.ChannelItem

class ChannelListDataSource(){
    fun getChannels(onDataReady: (ArrayList<ChannelItem>)-> Unit){
        val channels = arrayListOf<ChannelItem>(ChannelItem("first"),
            ChannelItem("second")
        )
        onDataReady(channels)
    }
}