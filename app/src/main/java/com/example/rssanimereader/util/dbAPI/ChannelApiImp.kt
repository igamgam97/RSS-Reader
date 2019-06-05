package com.example.rssanimereader.util.dbAPI

import com.example.rssanimereader.entity.ChannelItem

interface ChannelApiImp {
    fun getChannels(): ArrayList<ChannelItem>
    fun deleteChannel(channelName: String): ArrayList<ChannelItem>
    fun getUrlChannels(): ArrayList<String>
    fun retractSaveChannel(channelItem: ChannelItem): Long
}