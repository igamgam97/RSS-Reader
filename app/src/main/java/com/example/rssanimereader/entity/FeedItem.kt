package com.example.rssanimereader.entity

import java.io.Serializable

data class FeedItem(
        var id: Long?,
        val itemTitle: String, val itemDesc: String,
        val itemLink: String, val itemPubDate: String,
        val itemFavorite: Boolean, val linkChannel: String
) : Serializable {
    constructor(
            itemTitle: String, itemDesc: String,
            itemLink: String, itemPubDate: String,
            itemFavorite: Boolean, source: String
    ) : this(null, itemTitle, itemDesc, itemLink, itemPubDate, itemFavorite, source)
}
