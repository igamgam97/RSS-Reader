package com.example.rssanimereader.util.dbAPI

import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.entity.TitleFeedItem

interface GetLocalDataInterface {
    fun getItemFeeds(): List<FeedItem>
}