package com.example.rssanimereader.util.dbAPI

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context) : SQLiteOpenHelper(
        context,
        DATABASE_NAME, null,
        SCHEMA
) {

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(CREATE_TABLE_FEED)
        db.execSQL(CREATE_TABLE_CHANNEL)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $FEED_TABLE")
        db.execSQL("DROP TABLE IF EXISTS $CHANNEL_TABLE")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "rss_reader_store.db"
        private const val SCHEMA = 15
        const val FEED_TABLE = "feeds"
        const val FEED_COLUMN_ID = "_id"
        const val FEED_COLUMN_TITLE = "title"
        const val FEED_COLUMN_DESCRIPTION = "description"
        const val FEED_COLUMN_LINK = "feed_link"
        const val FEED_COLUMN_PUB_DATE = "pubDate"
        const val FEED_COLUMN_LINK_CHANNEL = "linkChannel"
        const val FEED_COLUMN_FAVORITE = "favorite"
        const val FEED_COLUMN_PATH_IMAGE = "pathImage"

        const val CREATE_TABLE_FEED = """CREATE TABLE $FEED_TABLE (
            $FEED_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $FEED_COLUMN_TITLE TEXT,
            $FEED_COLUMN_DESCRIPTION TEXT,
            $FEED_COLUMN_LINK TEXT,
            $FEED_COLUMN_PUB_DATE  TEXT,
            $FEED_COLUMN_FAVORITE INTEGER DEFAULT 0,
            $FEED_COLUMN_PATH_IMAGE TEXT,
            $FEED_COLUMN_LINK_CHANNEL  TEXT);"""

        const val CHANNEL_TABLE = "channels"
        const val CHANNEL__COLUMN_NAME = "name"
        const val CHANNEL_COLUMN_LINK = "link"
        const val CHANNEL_COLUMN_PATH_IMAGE = "path_image"
        const val CHANNEL_COLUMN_IMAGE = "pathImage"

        const val CREATE_TABLE_CHANNEL = """CREATE TABLE $CHANNEL_TABLE (
            $CHANNEL_COLUMN_LINK TEXT PRIMARY KEY,
            $CHANNEL__COLUMN_NAME TEXT,
            $CHANNEL_COLUMN_IMAGE TEXT,
            $CHANNEL_COLUMN_PATH_IMAGE TEXT);"""

    }


}