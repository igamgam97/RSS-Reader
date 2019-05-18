package com.example.rssanimereader.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import io.reactivex.Single
import io.reactivex.disposables.Disposables

class DownloadUrlSourceManager(private val context: Context) {

    private fun startService(path: String) {
        val intent = Intent(context, RSSDownloadService::class.java)
        intent.putExtra(FeedUtilConstants.URL, path)
        context.startService(intent)
    }

    fun validateData(path: String): Single<Intent> {
        startService(path)
        return context.observeBroadcasts(FeedUtilConstants.BROADCAST_STATE_DATA_ACTION)

    }

}

fun Context.observeBroadcasts(action: String) = observeBroadcasts(IntentFilter(action))

fun Context.observeBroadcasts(intentFilter: IntentFilter) = Single.create<Intent> { observer ->
    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            observer.onSuccess(intent)
        }
    }

    observer.setDisposable(Disposables.fromRunnable {
        unregisterReceiver(receiver)
    })

    registerReceiver(receiver, intentFilter)
}

