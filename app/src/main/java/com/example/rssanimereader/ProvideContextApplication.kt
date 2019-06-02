package com.example.rssanimereader

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.rssanimereader.di.Injection
import com.example.rssanimereader.util.dbAPI.DatabaseAPI
import com.example.rssanimereader.presentation.view.*


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
        dbConnection = DatabaseAPI(instance.applicationContext).open()
    }


    companion object {

        private lateinit var instance: ProvideContextApplication
        private lateinit var dbConnection: DatabaseAPI
        fun applicationContext(): Context = instance.applicationContext
        fun getDataBaseConnection() = dbConnection


    }



}
