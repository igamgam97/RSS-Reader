package com.example.rssanimereader.util.dbAPI

import android.os.Handler
import android.os.Looper
import android.os.Message


class DataBaseLoader(){
    private val mServiceLooper: Looper? = null
    private val mServiceHandler: ServiceHandler? = null

    private final class ServiceHandler(looper: Looper?) : Handler(looper) {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
        }
    }
}