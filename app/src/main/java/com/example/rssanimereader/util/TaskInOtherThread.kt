package com.example.rssanimereader.util

import android.os.Handler
import android.os.HandlerThread

class TaskInOtherThread {

    private lateinit var mWorkerThread: MyWorkerThread

    operator fun invoke(block: () -> Unit) {
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
        mWorkerThread.quitSafely()
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