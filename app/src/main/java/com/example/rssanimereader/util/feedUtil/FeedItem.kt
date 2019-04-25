package com.example.rssanimereader.util.feedUtil

import java.io.Serializable

data class FeedItem(
    var id: Long?,
    val itemTitle: String, val itemDesc: String,
    val itemLink: String, val itemPubDate: String
) : Serializable {
    constructor(
        itemTitle: String, itemDesc: String,
        itemLink: String, itemPubDate: String
    ) : this(null, itemTitle, itemDesc, itemLink, itemPubDate)
}
