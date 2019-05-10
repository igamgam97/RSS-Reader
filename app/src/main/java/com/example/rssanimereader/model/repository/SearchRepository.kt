package com.example.rssanimereader.model.repository

import com.example.rssanimereader.util.dbAPI.ChannelAPI

class SearchRepository(private val channelAPI: ChannelAPI) : Repository {


    fun getData(targetChannels: String, onDataReady: () -> Unit) {
        //val preferences = context.getSharedPreferences(FAVORITE_CHANNELS, Context.MODE_PRIVATE)
        //val channelAPI = ChannelAPI(context,preferences)
        //channelAPI.addChannel(targetChannels)
        onDataReady()

    }
}