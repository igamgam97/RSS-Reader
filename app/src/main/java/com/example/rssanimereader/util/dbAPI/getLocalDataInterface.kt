package com.example.rssanimereader.util.dbAPI

import com.example.rssanimereader.entity.FeedItem

interface GetLocalDataInterface {
    fun getItemFeeds(): List<FeedItem>
}