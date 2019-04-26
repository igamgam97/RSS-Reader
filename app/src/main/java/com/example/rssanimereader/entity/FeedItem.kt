package com.example.rssanimereader.entity

import java.io.Serializable

data class FeedItem(
    var id: Long?,
    val itemTitle: String, val itemDesc: String,
    val itemLink: String, val itemPubDate: String,
    val source: String
) : Serializable {
    constructor(
        itemTitle: String, itemDesc: String,
        itemLink: String, itemPubDate: String,
        source: String
    ) : this(null, itemTitle, itemDesc, itemLink, itemPubDate, source)
}
