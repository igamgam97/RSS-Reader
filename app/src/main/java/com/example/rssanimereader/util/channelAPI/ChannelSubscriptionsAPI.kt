package com.example.rssanimereader.util.channelAPI

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.rssanimereader.util.dbAPI.DataBaseLoader
import com.example.rssanimereader.util.dbAPI.DatabaseAPI

class ChannelSubscriptionsAPI(
    val context: Context,
    val preferences: SharedPreferences,
    val dataBaseLoader: DataBaseLoader
) {


    fun addChannel(targetChannel: String) {
        val listOfExistingChannels = getListChannel()
        Log.d("bag", listOfExistingChannels.toString())
        val newSet = HashSet<String>()
        newSet.addAll(listOfExistingChannels)
        newSet.add(targetChannel)
        preferences
            .edit()
            .putStringSet(KEY_CHANNEL, newSet)
            .apply()
        Log.d("bag", newSet.toString())
    }

    fun getListChannel(): MutableSet<String> {

        return preferences.getStringSet(KEY_CHANNEL, HashSet<String>()) ?: HashSet()
    }

    fun deleteChannel(channel:String) {

    }

    companion object {
        const val FAVORITE_CHANNELS = "Favorite_Channels"
        const val KEY_CHANNEL = "KEY_CHANNEL"
    }

}