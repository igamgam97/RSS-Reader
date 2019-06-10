package com.example.rssanimereader.peridic_feed_manager

import android.content.Context
import android.os.Build
import androidx.work.*
import com.example.rssanimereader.data.dataSource.localDS.LocalDS
import com.example.rssanimereader.data.dataSource.localDS.dbAPI.FeedAndChannelApi
import com.example.rssanimereader.data.dataSource.webDS.WebDS
import com.example.rssanimereader.data.dataSource.webDS.webApi.web.IWebApi
import com.example.rssanimereader.data.dataSource.webDS.webApi.web.NewImageSaver
import com.example.rssanimereader.data.dataSource.webDS.webApi.web.RSSRemoteDataParserI
import com.example.rssanimereader.data.repository.ChannelsRepository
import com.example.rssanimereader.data.repository.FeedsRepository
import com.example.rssanimereader.domain.use_case.SavePeriodicallyAllFeedsWebUseCase
import com.example.rssanimereader.notifications.NotificationsUtil
import com.example.rssanimereader.util.NetManager
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit


class PeriodicDownloadFeedsWorker(val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    private val compositeDisposable = CompositeDisposable()

    override fun doWork() = try {
        val dataBaseConnection = FeedAndChannelApi(context).open()
        val rssRemoteDataParser = RSSRemoteDataParserI()
        val imageSaver = NewImageSaver()
        val webApi = IWebApi(rssRemoteDataParser, imageSaver)
        val webDS = WebDS(webApi)
        val localDS = LocalDS(dataBaseConnection)
        val feedsRepository = FeedsRepository(webDS, localDS)
        val channelsRepository = ChannelsRepository(webDS, localDS)
        val netManager = NetManager(context)
        val savePeriodicallyAllFeedsWebUseCase =
            SavePeriodicallyAllFeedsWebUseCase(feedsRepository, channelsRepository, netManager)
        val disposable = savePeriodicallyAllFeedsWebUseCase()
            .subscribe({
                NotificationsUtil.showPeriodicNotificationOfDownloadFeeds(
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
                 .setRequiresDeviceIdle(false)
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
        .setInitialDelay(1, TimeUnit.HOURS)
        val myWork = myWorkBuilder.build()
        WorkManager.getInstance()
            .enqueueUniquePeriodicWork("jobTag", ExistingPeriodicWorkPolicy.KEEP, myWork)
    }
}