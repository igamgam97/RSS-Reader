package com.example.rssanimereader.peridic_feed_manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.example.rssanimereader.R
import com.example.rssanimereader.util.dbAPI.DatabaseAPI
import com.example.rssanimereader.util.feedUtil.parser.RSSRemoteDataParser
import com.example.rssanimereader.presentation.view.MainActivity
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit


class PeriodicDownloadFeedsWorker(val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    private val compositeDisposable = CompositeDisposable()
    fun showPeriodicNotificationOfDownloadFeeds(title: String, text: String, id: Int){
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(Constants.EXTRA_ID, id)

        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(applicationContext, "default")
            .setContentTitle(title)
            .setContentText(text)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)

        notificationManager.notify(id, notification.build())
    }

    override fun doWork() = try {
        val dataBaseConnection = DatabaseAPI(context).open()
        val rssRemoteDataParser = RSSRemoteDataParser()
        val saveDataFromWeb = SaveDataFromWeb(rssRemoteDataParser, dataBaseConnection)
        val disposable = saveDataFromWeb.downloadAndSaveAllFeedsApi()
            .subscribe ({
           showPeriodicNotificationOfDownloadFeeds(
                    "New feedsDownloads", "We downloads feeds for you",
                    1
                )
        }, {error -> onError()})
        compositeDisposable.add(disposable)
        Result.success()
    } catch (e: Exception) {
        Result.retry()
    }

    private fun onError() = Result.retry()

    override fun onStopped() {
        super.onStopped()
        compositeDisposable.clear()
    }
}


object Constants {
    const val EXTRA_ID = "id"
}


object PeriodicDownloadFeedsWorkerUtils {
    fun startPeriodicDownloadFeedsWorker() {
        //todo придумать как запустить ограничения на более раних устройствах
        val constraints = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
               /* .setRequiresDeviceIdle(false)*/
                .build()
        } else {
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        }
        val myWorkBuilder = PeriodicWorkRequest.Builder(
            PeriodicDownloadFeedsWorker::class.java,
            30,
            TimeUnit.MINUTES,
            25,
            TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            /*.setInitialDelay(1, TimeUnit.HOURS)*/
        val myWork = myWorkBuilder.build()
        WorkManager.getInstance()
            .enqueueUniquePeriodicWork("jobTag", ExistingPeriodicWorkPolicy.KEEP, myWork)
    }
}