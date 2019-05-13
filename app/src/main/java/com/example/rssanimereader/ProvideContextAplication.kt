package com.example.rssanimereader

import android.app.Application
import android.content.Context


class ProvideContextAplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: ProvideContextAplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

}