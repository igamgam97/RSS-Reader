package com.example.rssanimereader.util.dbAPI


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.core.database.getStringOrNull
import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.entity.FeedItem
import java.io.Closeable


class DatabaseAPI(context: Context) : GetLocalDataInterface, Closeable {


    private val dbHelper: DatabaseHelper =
        DatabaseHelper(context.applicationContext)
    private var database: SQLiteDatabase? = null

    /*private fun getEntriesByClause(whereClause: String? = null, whereArgs: Array<String>? = null): Cursor {
        val columns = arrayOf(
            DatabaseHelper.FEED_COLUMN_ID,
            DatabaseHelper.FEED_COLUMN_TITLE, DatabaseHelper.FEED_COLUMN_DESCRIPTION,
            DatabaseHelper.FEED_COLUMN_LINK, DatabaseHelper.FEED_COLUMN_PUB_DATE,
            DatabaseHelper.FEED_COLUMN_FAVORITE, DatabaseHelper.FEED_COLUMN_LINK_CHANNEL,
            DatabaseHelper.FEED_COLUMN_DOWNLOAD_DATE
        )
        return database!!.query(DatabaseHelper.FEED_TABLE, columns, whereClause, whereArgs, null, null, null)
    }*/

    override fun getItemFeeds(whereClause: String?, whereArgs: Array<String>?): ArrayList<FeedItem> {
        val items = ArrayList<FeedItem>()
        /*val cursor = getEntriesByClause(whereClause, whereArgs)*/
        val cursor = database!!.query(
            DatabaseHelper.FEED_TABLE,
            null, null, null, null, null, null
        )
        if (cursor.moveToFirst()) {
            do {
                with(cursor) {
                    val title = getString(cursor.getColumnIndex(DatabaseHelper.FEED_COLUMN_TITLE))
                    val description = getString(cursor.getColumnIndex(DatabaseHelper.FEED_COLUMN_DESCRIPTION))
                    val link = getString(cursor.getColumnIndex(DatabaseHelper.FEED_COLUMN_LINK))
                    val pubDate = getString(cursor.getColumnIndex(DatabaseHelper.FEED_COLUMN_PUB_DATE))
                    val favorite = getInt(cursor.getColumnIndex(DatabaseHelper.FEED_COLUMN_FAVORITE)) == 1
                    val downloadDate = getStringOrNull(cursor.getColumnIndex(DatabaseHelper.FEED_COLUMN_DOWNLOAD_DATE))
                    Log.d("bag",downloadDate.toString())
                    val pathImage = getStringOrNull(cursor.getColumnIndex(DatabaseHelper.FEED_COLUMN_PATH_IMAGE))
                    items.add(
                        FeedItem(
                            title,
                            description,
                            link,
                            pubDate,
                            favorite,
                          /*  downloadDate,*/
                            "",
                            pathImage
                        )
                    )
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        return items
    }

    val count: Long
        get() = DatabaseUtils.queryNumEntries(database, DatabaseHelper.FEED_TABLE)

    fun open(): DatabaseAPI {
        database = dbHelper.writableDatabase
        return this
    }

    override fun close() {
        dbHelper.close()
    }

    /*fun getItemFeed(id: Long): FeedItem? {
        var item: FeedItem?=null
        val query=String.format("SELECT * FROM %s WHERE %s=?", DatabaseHelper.FEED_TABLE, DatabaseHelper.FEED_COLUMN_ID)
        val cursor=database!!.rawQuery(query, arrayOf(id.toString()))
        if (cursor.moveToFirst()) {
            val title=cursor.getString(cursor.getColumnIndex(DatabaseHelper.FEED_COLUMN_TITLE))
            val description=cursor.getString(cursor.getColumnIndex(DatabaseHelper.FEED_COLUMN_DESCRIPTION))
            val link=cursor.getString(cursor.getColumnIndex(DatabaseHelper.FEED_COLUMN_LINK))
            val pubDate=cursor.getString(cursor.getColumnIndex(DatabaseHelper.FEED_COLUMN_PUB_DATE))
            val linkChannel=cursor.getString(cursor.getColumnIndex(DatabaseHelper.FEED_COLUMN_LINK_CHANNEL))
            item=
                FeedItem(id, title, description, link, pubDate, linkChannel)
        }
        cursor.close()
        return item
    }*/

    fun deleteChannel(channelLink: String): Long {

        val whereClause = "link = ?"
        val whereArgs = arrayOf(channelLink)
        return database!!.delete(DatabaseHelper.CHANNEL_TABLE, whereClause, whereArgs).toLong()
    }

    fun getAllChannels(): ArrayList<ChannelItem> {
        val items = ArrayList<ChannelItem>()
        val cursor = database!!.query(
            DatabaseHelper.CHANNEL_TABLE,
            null, null, null, null, null, null
        )
        if (cursor.moveToFirst()) {
            do {
                val link = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CHANNEL_COLUMN_LINK))
                val imagePath = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CHANNEL_COLUMN_PATH_IMAGE))
                val nameChannel = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CHANNEL__COLUMN_NAME))
                val image = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CHANNEL_COLUMN_IMAGE))
                items.add(ChannelItem(link, nameChannel, imagePath, image))

            } while (cursor.moveToNext())

        }
        cursor.close()
        return items
    }

    fun getAllUrlChannels():ArrayList<String>{
        val items = ArrayList<String>()
        val cursor = database!!.query(
            DatabaseHelper.CHANNEL_TABLE,
            null, null, null, null, null, null
        )
        if (cursor.moveToFirst()) {
            do {
                val link = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CHANNEL_COLUMN_LINK))
                items.add(link)
            } while (cursor.moveToNext())

        }
        cursor.close()
        return items
    }

    fun insert(item: FeedItem): Long {
        val cv = ContentValues()
        cv.put(DatabaseHelper.FEED_COLUMN_TITLE, item.itemTitle)
        cv.put(DatabaseHelper.FEED_COLUMN_DESCRIPTION, item.itemDesc)
        cv.put(DatabaseHelper.FEED_COLUMN_LINK, item.itemLink)
        cv.put(DatabaseHelper.FEED_COLUMN_PUB_DATE, item.itemPubDate)
        /*cv.put(DatabaseHelper.FEED_COLUMN_LINK_CHANNEL, item.linkChannel)*/
        return database!!.insert(DatabaseHelper.FEED_TABLE, null, cv)
    }


    fun insertAllFeeds(items: List<FeedItem>,channel:String) {
        database?.beginTransaction()
        items.forEach {
            val cv = ContentValues().apply {
                put(DatabaseHelper.FEED_COLUMN_TITLE, it.itemTitle)
                put(DatabaseHelper.FEED_COLUMN_DESCRIPTION, it.itemDesc)
                put(DatabaseHelper.FEED_COLUMN_LINK, it.itemLink)
                put(DatabaseHelper.FEED_COLUMN_PUB_DATE, it.itemPubDate)
                put(DatabaseHelper.FEED_COLUMN_LINK_CHANNEL, channel)
                put(DatabaseHelper.FEED_COLUMN_DOWNLOAD_DATE, it.downloadDate)
                put(DatabaseHelper.FEED_COLUMN_PATH_IMAGE, it.pathImage)
            }
            database!!.insert(DatabaseHelper.FEED_TABLE, null, cv)
        }
        database?.setTransactionSuccessful()
        database?.endTransaction()
    }

    fun insertChannel(channel: ChannelItem): Long {
        val cv = ContentValues().apply {
            put(DatabaseHelper.CHANNEL_COLUMN_LINK, channel.linkChannel)
            put(DatabaseHelper.CHANNEL__COLUMN_NAME, channel.nameChannel)
            put(DatabaseHelper.CHANNEL_COLUMN_PATH_IMAGE, channel.urlImage)
            put(DatabaseHelper.CHANNEL_COLUMN_IMAGE, channel.pathImage!!)
        }
        return database!!.insert(DatabaseHelper.CHANNEL_TABLE, null, cv)
    }


    fun delete(itemFeedId: Long): Long {

        val whereClause = "_id = ?"
        val whereArgs = arrayOf(itemFeedId.toString())
        return database!!.delete(DatabaseHelper.FEED_TABLE, whereClause, whereArgs).toLong()
    }


    fun updateFeed(item: FeedItem): Long {

        val whereClause = "${DatabaseHelper.FEED_COLUMN_TITLE} = ?"
        val whereArgs = arrayOf(item.itemTitle)
        val cv = ContentValues()
        if (item.itemFavorite) {
            cv.put(DatabaseHelper.FEED_COLUMN_FAVORITE, 1)
        } else {
            cv.put(DatabaseHelper.FEED_COLUMN_FAVORITE, 0)
        }
        return database!!.update(DatabaseHelper.FEED_TABLE, cv, whereClause, whereArgs).toLong()
    }


    fun deleteFeedsByChannel(channel: String): Long {
        val whereClause = "${DatabaseHelper.FEED_COLUMN_LINK_CHANNEL} = ?"
        /* val whereClause = "linkChannel = ?"*/
        val whereArgs = arrayOf(channel)
        return database!!.delete(DatabaseHelper.FEED_TABLE, whereClause, whereArgs).toLong()
    }


    fun getFeedsByChannel(channel: String): ArrayList<FeedItem> {
        val whereClause = "${DatabaseHelper.FEED_COLUMN_LINK_CHANNEL} = ?"
        /*val whereClause = "linkChannel = ?"*/
        val whereArgs = arrayOf(channel)
        return getItemFeeds(whereClause, whereArgs)
    }

    fun getFavoriteFeeds(): ArrayList<FeedItem> {
        val whereClause = "${DatabaseHelper.FEED_COLUMN_FAVORITE} = ?"
        val whereArgs = arrayOf(1.toString())
        return getItemFeeds(whereClause, whereArgs)
    }

    fun isExistChannel(channel: String): Boolean {
        val whereClause = "${DatabaseHelper.CHANNEL_COLUMN_LINK} = ?"
        /*val whereClause = "link = ?"*/
        val whereArgs = arrayOf(channel)

        val cursor = database!!.query(
            DatabaseHelper.CHANNEL_TABLE, null, whereClause,
            whereArgs, null, null, null
        )
        if (cursor.count <= 0) {
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }


}