package com.example.rssanimereader.service

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.example.rssanimereader.di.Injection
import io.reactivex.disposables.CompositeDisposable


class RSSDownloadService : IntentService("RSSDownloadService") {

    private val compositeDisposable = CompositeDisposable()

    override fun onHandleIntent(intent: Intent) {
        val urlPath = intent.getStringExtra(FeedUtilConstants.URL)

        val remoteDataSaver = Injection.provideRemoteDataSaver()

        val disposable = remoteDataSaver.saveDataFromApi(urlPath)
            .subscribe({ isDataPublishedSuccessful() }, { error -> isDataPublishedError(error) })
        compositeDisposable.add(disposable)

    }


    private fun isDataPublishedSuccessful() {
        val intent = Intent(FeedUtilConstants.BROADCAST_STATE_DATA_ACTION)
        /*intent.putExtra(FeedUtilConstants.STATUS_DATA, statusData)*/
        sendBroadcast(intent)
    }

    private fun isDataPublishedError(error:Throwable) {
        when(error){

        }
        val intent = Intent(FeedUtilConstants.BROADCAST_STATE_DATA_ACTION)
        intent.putExtra(FeedUtilConstants.STATUS_DATA, error)
        sendBroadcast(intent)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }



}

