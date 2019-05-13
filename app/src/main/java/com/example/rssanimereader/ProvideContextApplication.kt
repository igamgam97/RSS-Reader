package com.example.rssanimereader

import android.app.Application
import android.content.Context


class ProvideContextApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: ProvideContextApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

}