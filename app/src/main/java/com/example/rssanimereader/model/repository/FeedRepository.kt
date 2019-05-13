package com.example.rssanimereader.model.repository

import com.example.rssanimereader.entity.FeedItem

class FeedRepository() : Repository {

    fun getHTMLTFeed(item: FeedItem, onDataReady: (String) -> Unit) {
    }
}
