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

        db.execSQL(
            "CREATE TABLE " + TABLE + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_LINK + " TEXT, " +
                    COLUMN_PUBDATE + " TEXT, " +
                    COLUMN_SOURCE + " TEXT);"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "rss_reader_store.db"
        private const val SCHEMA = 6
        internal const val TABLE = "feeds"
        const val COLUMN_ID = "_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_LINK = "feed_link"
        const val COLUMN_PUBDATE = "pubDate"
        const val COLUMN_SOURCE = "source"
    }
}