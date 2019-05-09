package com.example.rssanimereader.util.dbAPI


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.entity.FeedItem
import java.io.Closeable


class DatabaseAPI(context: Context) : GetLocalDataInterface, Closeable {

    init {

    }


    private val dbHelper: DatabaseHelper=
        DatabaseHelper(context.applicationContext)
    private var database: SQLiteDatabase?=null

    private fun getEntriesByClause(whereClause: String?=null, whereArgs: Array<String>?=null): Cursor {
        val columns=arrayOf(
            DatabaseHelper.FEED_COLUMN_ID,
            DatabaseHelper.FEED_COLUMN_TITLE, DatabaseHelper.FEED_COLUMN_DESCRIPTION,
            DatabaseHelper.FEED_COLUMN_LINK, DatabaseHelper.FEED_COLUMN_PUB_DATE,
            DatabaseHelper.FEED_COLUMN_SOURCE
        )
        return database!!.query(DatabaseHelper.FEED_TABLE, columns, whereClause, whereArgs, null, null, null)
    }

    override fun getItemFeeds(whereClause: String?, whereArgs: Array<String>?): List<FeedItem> {
        val items=ArrayList<FeedItem>()
        val cursor=getEntriesByClause(whereClause, whereArgs)
        if (cursor.moveToFirst()) {
            do {
                val id=cursor.getLong(cursor.getColumnIndex(DatabaseHelper.FEED_COLUMN_ID))
                val title=cursor.getString(cursor.getColumnIndex(DatabaseHelper.FEED_COLUMN_TITLE))
                val description=cursor.getString(cursor.getColumnIndex(DatabaseHelper.FEED_COLUMN_DESCRIPTION))
                val link=cursor.getString(cursor.getColumnIndex(DatabaseHelper.FEED_COLUMN_LINK))
                val pubDate=cursor.getString(cursor.getColumnIndex(DatabaseHelper.FEED_COLUMN_PUB_DATE))
                val source=cursor.getString(cursor.getColumnIndex(DatabaseHelper.FEED_COLUMN_SOURCE))
                items.add(
                    FeedItem(
                        id,
                        title,
                        description,
                        link,
                        pubDate,
                        source
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return items
    }

    val count: Long
        get()=DatabaseUtils.queryNumEntries(database, DatabaseHelper.FEED_TABLE)

    fun open(): DatabaseAPI {
        database=dbHelper.writableDatabase
        return this
    }

    override fun close() {
        dbHelper.close()
    }

    fun getItemFeed(id: Long): FeedItem? {
        var item: FeedItem?=null
        val query=String.format("SELECT * FROM %s WHERE %s=?", DatabaseHelper.FEED_TABLE, DatabaseHelper.FEED_COLUMN_ID)
        val cursor=database!!.rawQuery(query, arrayOf(id.toString()))
        if (cursor.moveToFirst()) {
            val title=cursor.getString(cursor.getColumnIndex(DatabaseHelper.FEED_COLUMN_TITLE))
            val description=cursor.getString(cursor.getColumnIndex(DatabaseHelper.FEED_COLUMN_DESCRIPTION))
            val link=cursor.getString(cursor.getColumnIndex(DatabaseHelper.FEED_COLUMN_LINK))
            val pubDate=cursor.getString(cursor.getColumnIndex(DatabaseHelper.FEED_COLUMN_PUB_DATE))
            val source=cursor.getString(cursor.getColumnIndex(DatabaseHelper.FEED_COLUMN_SOURCE))
            item=
                FeedItem(id, title, description, link, pubDate, source)
        }
        cursor.close()
        return item
    }

    fun deleteChannel(channelName: String): Long {

        val whereClause="name = ?"
        val whereArgs=arrayOf(channelName)
        return database!!.delete(DatabaseHelper.CHANNEL_TABLE, whereClause, whereArgs).toLong()
    }

    fun getAllChannels(): ArrayList<ChannelItem> {
        val items = ArrayList<ChannelItem>()
        val cursor=database!!.query(DatabaseHelper.CHANNEL_TABLE,
            null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val link=cursor.getString(cursor.getColumnIndex(DatabaseHelper.CHANNEL_COLUMN_NAME))
                val imagePath=cursor.getString(cursor.getColumnIndex(DatabaseHelper.CHANNEL_COLUMN_PATH_IMAGE))
                items.add(ChannelItem(link,"", imagePath))

            }while(cursor.moveToNext())

        }
        cursor.close()
        return items
    }

    fun insert(item: FeedItem): Long {
        val cv=ContentValues()
        cv.put(DatabaseHelper.FEED_COLUMN_TITLE, item.itemTitle)
        cv.put(DatabaseHelper.FEED_COLUMN_DESCRIPTION, item.itemDesc)
        cv.put(DatabaseHelper.FEED_COLUMN_LINK, item.itemLink)
        cv.put(DatabaseHelper.FEED_COLUMN_PUB_DATE, item.itemPubDate)
        cv.put(DatabaseHelper.FEED_COLUMN_SOURCE, item.source)
        return database!!.insert(DatabaseHelper.FEED_TABLE, null, cv)
    }


    fun insertAll(items: List<FeedItem>) {
        database?.beginTransaction()
        items.forEach {
            val cv=ContentValues()
            cv.put(DatabaseHelper.FEED_COLUMN_TITLE, it.itemTitle)
            cv.put(DatabaseHelper.FEED_COLUMN_DESCRIPTION, it.itemDesc)
            cv.put(DatabaseHelper.FEED_COLUMN_LINK, it.itemLink)
            cv.put(DatabaseHelper.FEED_COLUMN_PUB_DATE, it.itemPubDate)
            cv.put(DatabaseHelper.FEED_COLUMN_SOURCE, it.source)
            database!!.insert(DatabaseHelper.FEED_TABLE, null, cv)
        }
        database?.setTransactionSuccessful()
        database?.endTransaction()
    }

    fun insertChannel(channel: ChannelItem): Long {
        val cv=ContentValues()
        cv.put(DatabaseHelper.CHANNEL_COLUMN_NAME, channel.linkChannel)
        cv.put(DatabaseHelper.CHANNEL_COLUMN_PATH_IMAGE, channel.pathImage)
        return database!!.insert(DatabaseHelper.CHANNEL_TABLE, null, cv)
    }


    fun delete(itemFeedId: Long): Long {

        val whereClause="_id = ?"
        val whereArgs=arrayOf(itemFeedId.toString())
        return database!!.delete(DatabaseHelper.FEED_TABLE, whereClause, whereArgs).toLong()
    }

    fun update(item: FeedItem): Long {

        val whereClause=DatabaseHelper.FEED_COLUMN_ID + "=" + item.id.toString()
        val cv=ContentValues()
        cv.put(DatabaseHelper.FEED_COLUMN_TITLE, item.itemTitle)
        cv.put(DatabaseHelper.FEED_COLUMN_DESCRIPTION, item.itemDesc)
        cv.put(DatabaseHelper.FEED_COLUMN_LINK, item.itemLink)
        cv.put(DatabaseHelper.FEED_COLUMN_PUB_DATE, item.itemPubDate)
        cv.put(DatabaseHelper.FEED_COLUMN_SOURCE, item.source)
        return database!!.update(DatabaseHelper.FEED_TABLE, cv, whereClause, null).toLong()
    }


    fun deleteFeedsByChannel(channel: String): Long {
        val whereClause="source = ?"
        val whereArgs=arrayOf(channel)
        return database!!.delete(DatabaseHelper.FEED_TABLE, whereClause, whereArgs).toLong()
    }


    fun getFeedsByChannel(channel: String):ArrayList<FeedItem> {
        val whereClause="source = ?"
        val whereArgs=arrayOf(channel)
        return getItemFeeds(whereClause, whereArgs) as ArrayList<FeedItem>
    }

    fun isExistChannel(channel: String) :Boolean{
        val whereClause="name = ?"
        val whereArgs=arrayOf(channel)

        val cursor=database!!.query(DatabaseHelper.CHANNEL_TABLE, null, whereClause,
            whereArgs, null, null, null)
        if (cursor.count <= 0) {
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }

}