package com.example.rssanimereader.model

import android.content.Context
import com.example.rssanimereader.entity.FeedItem

class SearchRepository(private val context: Context) {



    fun getData(onDataReady: (ArrayList<FeedItem>) -> Unit) {

        val Favorite_Channels = "Favorite_Channels"

        val preferences = context.getSharedPreferences(Favorite_Channels, Context.MODE_PRIVATE)

        val listOfExistingChannels = preferences.getStringSet("key",null)!!
        val editor = preferences.edit()
        val newSet = HashSet<String>()
        newSet.addAll(listOfExistingChannels)
        editor.putStringSet("key", newSet)
        editor.apply()



    }
}