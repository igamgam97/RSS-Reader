package com.example.rssanimereader

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.example.rssanimereader.data.dataSource.localDS.dbAPI.FeedAndChannelApi


class ProvideContextApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val nightModeEnabled = prefs.getBoolean("NIGHT_MODE_VALUE", false)
        if (nightModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        instance = this
        dbConnection = FeedAndChannelApi(instance.applicationContext).open()
    }


    companion object {
        private lateinit var instance: ProvideContextApplication
        private lateinit var dbConnection: FeedAndChannelApi
        fun applicationContext(): Context = instance.applicationContext
        fun getDataBaseConnection() = dbConnection


    }



}
