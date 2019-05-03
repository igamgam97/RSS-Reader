package com.example.rssanimereader.model

import android.content.Context

class SearchRepository(private val context: Context) {


    fun getData(targetChannels: String, onDataReady: () -> Unit) {

        val Favorite_Channels = "Favorite_Channels"

        val KEY_CHANNEL = "KEY_CHANNEL"

        val preferences = context.getSharedPreferences(Favorite_Channels, Context.MODE_PRIVATE)

        val listOfExistingChannels =
            preferences.getStringSet(KEY_CHANNEL, null) ?: HashSet<String>()
        val editor = preferences.edit()
        val newSet = HashSet<String>()
        listOfExistingChannels.add(targetChannels)
        newSet.addAll(listOfExistingChannels)
        editor.putStringSet(KEY_CHANNEL, newSet)
        editor.apply()
        onDataReady()

    }
}