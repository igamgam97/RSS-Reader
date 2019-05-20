package com.example.rssanimereader.entity

import java.io.Serializable

data class FeedItem(
    val itemTitle: String, var itemDesc: String,
    val itemLink: String, val itemPubDate: String,
    val itemFavorite: Boolean, val linkChannel: String,
    val pathImage:String?
)
