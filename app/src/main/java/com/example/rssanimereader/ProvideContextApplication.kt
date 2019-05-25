package com.example.rssanimereader

import android.app.Application
import android.content.Context
import com.example.rssanimereader.util.dbAPI.DatabaseAPI


class ProvideContextApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        instance = this
        dataBaseConnection = DatabaseAPI(instance.applicationContext).open()
    }


    companion object {
        lateinit var instance: ProvideContextApplication
        private var dataBaseConnection: DatabaseAPI? = null
        fun applicationContext() : Context {
            return instance.applicationContext
        }

        fun getDataBaseConnection() = dataBaseConnection!!
    }



}
