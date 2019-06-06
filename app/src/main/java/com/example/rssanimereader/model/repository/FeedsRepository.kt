package com.example.rssanimereader.model.repository

import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.model.dataSource.LocalDS
import com.example.rssanimereader.web.WebApi
import io.reactivex.Completable

class FeedsRepository(
    val webApi: WebApi,
    val localDS: LocalDS
){
    fun setFavoriteFeed(feed: FeedItem): Completable = localDS.setFavoriteFeed(feed)
    fun setIsRead(feed: FeedItem): Completable = localDS.setIsRead(feed)

}