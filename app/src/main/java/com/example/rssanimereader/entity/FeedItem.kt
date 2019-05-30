package com.example.rssanimereader.entity

data class FeedItem(
    val itemTitle: String, var itemDesc: String,
    val itemLink: String, val itemPubDate: String,
    var itemFavorite: Boolean, val downloadDate: String,
    val pathImage:String?
)
