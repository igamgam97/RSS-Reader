package com.example.rssanimereader.domain.entity

data class FeedItem(
    val itemTitle: String, var itemDesc: String,
    val itemLink: String, val itemPubDate: String,
    var itemFavorite: Boolean, val downloadDate: String,
    val pathImage: String?, var isRead: Boolean
) : Comparable<FeedItem> {
    override fun compareTo(other: FeedItem): Int = when {
        downloadDate < other.downloadDate -> -1
        downloadDate > other.downloadDate -> 1
        else -> 0
    }
}
