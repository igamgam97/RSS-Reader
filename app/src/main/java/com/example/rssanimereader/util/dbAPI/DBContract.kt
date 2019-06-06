package com.example.rssanimereader.util.dbAPI

object DBContract {
    const val DATABASE_NAME = "rss_reader_store.db"
    const val SCHEMA = 21

    object FeedTable {
        const val TABLE_NAME = "feeds"
        const val COLUMN_ID = "_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_LINK = "feed_link"
        const val COLUMN_PUB_DATE = "pubDate"
        const val COLUMN_LINK_CHANNEL = "linkChannel"
        const val COLUMN_FAVORITE = "favorite"
        const val COLUMN_PATH_IMAGE = "pathImage"
        const val COLUMN_DOWNLOAD_DATE = "downloadDate"
        const val COLUMN_IS_READ = "isRead"
    }

    object ChannelTable {
        const val TABLE_NAME = "channels"
        const val COLUMN_NAME = "name"
        const val COLUMN_LINK = "link"
        const val COLUMN_PATH_IMAGE = "path_image"
        const val COLUMN_IMAGE = "pathImage"
    }
}