package com.example.rssanimereader.data.dataSource.localDS.dbAPI

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.rssanimereader.data.dataSource.localDS.dbAPI.contracts.DBContract


class DatabaseHelper(context: Context) : SQLiteOpenHelper(
        context,
        DBContract.DATABASE_NAME, null,
        DBContract.SCHEMA
) {

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(CREATE_TABLE_FEED)
        db.execSQL(CREATE_TABLE_CHANNEL)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DBContract.FeedTable.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${DBContract.ChannelTable.TABLE_NAME}")
        onCreate(db)
    }

    companion object {

        const val CREATE_TABLE_FEED = """CREATE TABLE ${DBContract.FeedTable.TABLE_NAME} (
            ${DBContract.FeedTable.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${DBContract.FeedTable.COLUMN_TITLE} TEXT UNIQUE,
            ${DBContract.FeedTable.COLUMN_DESCRIPTION} TEXT,
            ${DBContract.FeedTable.COLUMN_LINK} TEXT,
            ${DBContract.FeedTable.COLUMN_PUB_DATE}  TEXT,
            ${DBContract.FeedTable.COLUMN_FAVORITE} INTEGER DEFAULT 0,
            ${DBContract.FeedTable.COLUMN_PATH_IMAGE} TEXT,
            ${DBContract.FeedTable.COLUMN_LINK_CHANNEL}  TEXT,
            ${DBContract.FeedTable.COLUMN_IS_READ} INTEGER DEFAULT 0,
            ${DBContract.FeedTable.COLUMN_DOWNLOAD_DATE} TEXT);"""


        const val CREATE_TABLE_CHANNEL = """CREATE TABLE ${DBContract.ChannelTable.TABLE_NAME} (
            ${DBContract.ChannelTable.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${DBContract.ChannelTable.COLUMN_LINK} TEXT UNIQUE,
            ${DBContract.ChannelTable.COLUMN_NAME} TEXT,
            ${DBContract.ChannelTable.COLUMN_IMAGE} TEXT,
            ${DBContract.ChannelTable.COLUMN_PATH_IMAGE} TEXT);"""

    }


}