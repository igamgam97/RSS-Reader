package com.example.rssanimereader.util.feedUtil

import java.io.Serializable

data class FeedItem(
    val itemTitle: String, val itemDesc: String,
    val itemLink: String, val itemPubDate: String
) : Serializable
