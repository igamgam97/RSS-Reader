package com.example.rssanimereader.model.repository

import com.example.rssanimereader.util.channelAPI.ChannelSubscriptionsAPI

class SearchRepository(private val channelSubscriptionsAPI: ChannelSubscriptionsAPI) : Repository {


    fun getData(targetChannels: String, onDataReady: () -> Unit) {
        //val preferences = context.getSharedPreferences(FAVORITE_CHANNELS, Context.MODE_PRIVATE)
        //val channelSubscriptionsAPI = ChannelSubscriptionsAPI(context,preferences)
        channelSubscriptionsAPI.addChannel(targetChannels)
        onDataReady()

    }
}