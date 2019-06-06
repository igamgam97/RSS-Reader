package com.example.rssanimereader.peridic_feed_manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.example.rssanimereader.R
import com.example.rssanimereader.model.dataSource.localDS.dbAPI.FeedAndChannelApi
import com.example.rssanimereader.model.dataSource.webDS.webApi.web.NewImageSaver
import com.example.rssanimereader.model.dataSource.webDS.webApi.web.WebApi
import com.example.rssanimereader.model.dataSource.webDS.webApi.web.RSSRemoteDataParser
import com.example.rssanimereader.domain.usecase.SavePeriodicallyAllFeedsWebUseCase
import com.example.rssanimereader.model.dataSource.webDS.WebDS
import com.example.rssanimereader.model.dataSource.localDS.LocalDS
import com.example.rssanimereader.model.repository.ChannelsRepository
import com.example.rssanimereader.model.repository.FeedsRepository
import com.example.rssanimereader.presentation.view.MainActivity
import com.example.rssanimereader.util.NetManager
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit


class PeriodicDownloadFeedsWorker(val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    private val compositeDisposable = CompositeDisposable()
    fun showPeriodicNotificationOfDownloadFeeds(title: String, text: String, id: Int) {
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
        val dataBaseConnection = FeedAndChannelApi(context).open()
        val rssRemoteDataParser = RSSRemoteDataParser()
        val imageSaver = NewImageSaver()
        val webApi = WebApi(rssRemoteDataParser, imageSaver)
        val webDS = WebDS(webApi)
        val localDS = LocalDS(dataBaseConnection)
        val feedsRepository = FeedsRepository(webDS, localDS)
        val channelsRepository = ChannelsRepository(webDS, localDS)
        val netManager = NetManager(context)
        val savePeriodicallyAllFeedsWebUseCase =
            SavePeriodicallyAllFeedsWebUseCase(feedsRepository, channelsRepository, netManager)
        Log.d("bag","PeriodicDownloadFeedsWorker work")
        val disposable = savePeriodicallyAllFeedsWebUseCase()
            .subscribe({
                Log.d("bag","savePeriodicatllyAllFeeds work")
                showPeriodicNotificationOfDownloadFeeds(
                    "New feedsDownloads", "We downloads feeds for you",
                    1
                )
            }, { error -> onError() })
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