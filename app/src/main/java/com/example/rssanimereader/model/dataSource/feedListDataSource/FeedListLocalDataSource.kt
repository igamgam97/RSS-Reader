package com.example.rssanimereader.model.dataSource.feedListDataSource

import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.dbAPI.FeedApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class FeedListLocalDataSource(private val feedApi: FeedApi) :
    FeedListDataSource {



    override fun getFeedsByChannel(linkChannel: String) = Single.fromCallable<ArrayList<FeedItem>> {
        feedApi.getFeedsByChannel(linkChannel)
    }.subscribeOn(Schedulers.io())

    override fun getAllFeeds() = Single.fromCallable{feedApi.getAllFeeds()}.subscribeOn(Schedulers.io())

    fun getFavoriteFeeds() = Single.fromCallable{feedApi.getFavoriteFeeds()}.subscribeOn(Schedulers.io())
}

//todo переделать логику получения всех фидов
//todo добавить затеменине в уже прочитанные
//todo допилить add channel fragment
//todo впилить прочитанные
//todo добавить кнопу удалить прочитанные