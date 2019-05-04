package com.example.rssanimereader.util.dbAPI

import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import com.example.rssanimereader.entity.FeedItem


class DataBaseLoader(private val context: Context) {
    private lateinit var mWorkerThread: MyWorkerThread

    fun startTaskOnOtherThread(block: () -> Unit) {
        val mUiHandler = Handler()
        mWorkerThread = MyWorkerThread("myWorkerThread")
        val task = Runnable {
            mUiHandler.post {
                block()
            }
        }
        mWorkerThread.start()
        mWorkerThread.prepareHandler()
        mWorkerThread.postTask(task)
    }

    fun getData(onDataReady: (ArrayList<FeedItem>) -> Unit) {

        startTaskOnOtherThread {
            val dataBaseAPI = DatabaseAPI(context).open()
            val feeds = dataBaseAPI.getItemFeeds()
            dataBaseAPI.close()
            onDataReady(feeds as ArrayList<FeedItem>)
        }

    }

    fun deleteFeedsofChannel(channel:String) {
        startTaskOnOtherThread {
            val dataBaseAPI = DatabaseAPI(context).open()
            dataBaseAPI.deleteByChannel(channel)
            dataBaseAPI.close()
        }
    }

    fun close() {
        mWorkerThread.quit()
    }

    private inner class MyWorkerThread(name: String) : HandlerThread(name) {

        private var mWorkerHandler: Handler? = null

        fun postTask(task: Runnable) {
            mWorkerHandler!!.post(task)
        }


        fun prepareHandler() {
            mWorkerHandler = Handler(looper)
        }
    }
}
