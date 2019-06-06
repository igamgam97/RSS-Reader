package com.example.rssanimereader.util.dbAPI


import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import androidx.core.database.getStringOrNull
import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.web.ImageSaver
import java.io.Closeable


class DatabaseAPI(context: Context) : Closeable, ChannelAndFeedApi {
    private val dbHelper: DatabaseHelper =
        DatabaseHelper(context.applicationContext)
    private var database: SQLiteDatabase? = null


    fun open(): DatabaseAPI {
        database = dbHelper.writableDatabase
        return this
    }

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

    override fun getAllFeeds(whereClause: String?, whereArgs: Array<String>?): ArrayList<FeedItem> {
        val items = ArrayList<FeedItem>()
        val cursor = database!!.query(
            DBContract.FeedTable.TABLE_NAME,
            null, whereClause, whereArgs, null, null, "${DBContract.FeedTable.COLUMN_DOWNLOAD_DATE} DESC"
        )
        if (cursor.moveToFirst()) {
            do {
                with(cursor) {
                    val title = getString(cursor.getColumnIndex(DBContract.FeedTable.COLUMN_TITLE))
                    val description = getString(cursor.getColumnIndex(DBContract.FeedTable.COLUMN_DESCRIPTION))
                    val link = getString(cursor.getColumnIndex(DBContract.FeedTable.COLUMN_LINK))
                    val pubDate = getString(cursor.getColumnIndex(DBContract.FeedTable.COLUMN_PUB_DATE))
                    val favorite = getInt(cursor.getColumnIndex(DBContract.FeedTable.COLUMN_FAVORITE)) == 1
                    val isRead = getInt(cursor.getColumnIndex(DBContract.FeedTable.COLUMN_IS_READ)) == 1
                    val downloadDate = getString(cursor.getColumnIndex(DBContract.FeedTable.COLUMN_DOWNLOAD_DATE))
                    val pathImage = getStringOrNull(cursor.getColumnIndex(DBContract.FeedTable.COLUMN_PATH_IMAGE))
                    items.add(
                        FeedItem(
                            title,
                            description,
                            link,
                            pubDate,
                            favorite,
                            downloadDate,
                            pathImage,
                            isRead
                        )
                    )
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        return items
    }

    override val count: Long
        get() = DatabaseUtils.queryNumEntries(database, DBContract.FeedTable.TABLE_NAME)


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

    override fun getAllChannels(): ArrayList<ChannelItem> {
        val items = ArrayList<ChannelItem>()
        val cursor = database!!.query(
            DBContract.ChannelTable.TABLE_NAME,
            null, null, null, null, null, null
        )
        if (cursor.moveToFirst()) {
            do {
                val link = cursor.getString(cursor.getColumnIndex(DBContract.ChannelTable.COLUMN_LINK))
                val imagePath = cursor.getString(cursor.getColumnIndex(DBContract.ChannelTable.COLUMN_PATH_IMAGE))
                val nameChannel = cursor.getString(cursor.getColumnIndex(DBContract.ChannelTable.COLUMN_NAME))
                val image = cursor.getString(cursor.getColumnIndex(DBContract.ChannelTable.COLUMN_IMAGE))
                items.add(ChannelItem(link, nameChannel, imagePath, image))

            } while (cursor.moveToNext())

        }
        cursor.close()
        return items
    }

    override fun deleteChannel(channelLink: String): Long {
        deleteFeedsByChannel(channelLink)
        val whereClause = "link = ?"
        val whereArgs = arrayOf(channelLink)
        return database!!.delete(DBContract.ChannelTable.TABLE_NAME, whereClause, whereArgs).toLong()
    }

    override fun getAllUrlChannels(): ArrayList<String> {
        val items = ArrayList<String>()
        val cursor = database!!.query(
            DBContract.ChannelTable.TABLE_NAME,
            null, null, null, null, null, null
        )
        if (cursor.moveToFirst()) {
            do {
                val link = cursor.getString(cursor.getColumnIndex(DBContract.ChannelTable.COLUMN_LINK))
                items.add(link)
            } while (cursor.moveToNext())

        }
        cursor.close()
        return items
    }

    override fun insertFeeds(item: FeedItem): Long {
        val cv = ContentValues()
        cv.put(DBContract.FeedTable.COLUMN_TITLE, item.itemTitle)
        cv.put(DBContract.FeedTable.COLUMN_DESCRIPTION, item.itemDesc)
        cv.put(DBContract.FeedTable.COLUMN_LINK, item.itemLink)
        cv.put(DBContract.FeedTable.COLUMN_PUB_DATE, item.itemPubDate)
        /*cv.put(DatabaseHelper.FEED_COLUMN_LINK_CHANNEL, item.linkChannel)*/
        return database!!.insertWithOnConflict(DBContract.FeedTable.TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_IGNORE)
    }

    override fun insertChannel(channel: ChannelItem): Long {
        val cv = ContentValues().apply {
            put(DBContract.ChannelTable.COLUMN_LINK, channel.linkChannel)
            put(DBContract.ChannelTable.COLUMN_NAME, channel.nameChannel)
            put(DBContract.ChannelTable.COLUMN_PATH_IMAGE, channel.urlImage)
            put(DBContract.ChannelTable.COLUMN_IMAGE, channel.pathImage!!)
        }
        return database!!.insertWithOnConflict(DBContract.ChannelTable.TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_IGNORE)
    }


    override fun insertAllFeedsByChannel(items: List<FeedItem>, channel: String) {
        database?.beginTransaction()
        items.forEach {
            val cv = ContentValues().apply {
                put(DBContract.FeedTable.COLUMN_TITLE, it.itemTitle)
                put(DBContract.FeedTable.COLUMN_DESCRIPTION, it.itemDesc)
                put(DBContract.FeedTable.COLUMN_LINK, it.itemLink)
                put(DBContract.FeedTable.COLUMN_PUB_DATE, it.itemPubDate)
                put(DBContract.FeedTable.COLUMN_LINK_CHANNEL, channel)
                put(DBContract.FeedTable.COLUMN_DOWNLOAD_DATE, it.downloadDate)
                put(DBContract.FeedTable.COLUMN_PATH_IMAGE, it.pathImage)
            }
            database!!.insertWithOnConflict(DBContract.FeedTable.TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_IGNORE)
        }
        database?.setTransactionSuccessful()
        database?.endTransaction()
    }


    override fun setFavoriteFeed(item: FeedItem): Long {

        val whereClause = "${DBContract.FeedTable.COLUMN_TITLE} = ?"
        val whereArgs = arrayOf(item.itemTitle)
        val cv = ContentValues()
        if (item.itemFavorite) {
            cv.put(DBContract.FeedTable.COLUMN_FAVORITE, 1)
        } else {
            cv.put(DBContract.FeedTable.COLUMN_FAVORITE, 0)
        }
        return database!!.update(DBContract.FeedTable.TABLE_NAME, cv, whereClause, whereArgs).toLong()
    }

    override fun setIsReadFeed(item: FeedItem): Long {

        val whereClause = "${DBContract.FeedTable.COLUMN_TITLE} = ?"
        val whereArgs = arrayOf(item.itemTitle)
        val cv = ContentValues()
        cv.put(DBContract.FeedTable.COLUMN_IS_READ, 1)
        return database!!.update(DBContract.FeedTable.TABLE_NAME, cv, whereClause, whereArgs).toLong()
    }


    override fun deleteFeedsByChannel(channel: String): Long {
        val whereClause = "${DBContract.FeedTable.COLUMN_LINK_CHANNEL} = ?"
        /* val whereClause = "linkChannel = ?"*/
        val whereArgs = arrayOf(channel)
        return database!!.delete(DBContract.FeedTable.TABLE_NAME, whereClause, whereArgs).toLong()
    }


    override fun getFeedsByChannel(channel: String): ArrayList<FeedItem> {
        val whereClause = "${DBContract.FeedTable.COLUMN_LINK_CHANNEL} = ?"
        /*val whereClause = "linkChannel = ?"*/
        val whereArgs = arrayOf(channel)
        return getAllFeeds(whereClause, whereArgs)
    }

    override fun getFavoriteFeeds(): ArrayList<FeedItem> {
        val whereClause = "${DBContract.FeedTable.COLUMN_FAVORITE} = ?"
        val whereArgs = arrayOf(1.toString())
        return getAllFeeds(whereClause, whereArgs)
    }

    override fun isExistChannel(channel: String): Boolean {
        val whereClause = "${DBContract.ChannelTable.COLUMN_LINK} = ?"
        /*val whereClause = "link = ?"*/
        val whereArgs = arrayOf(channel)

        val cursor = database!!.query(
            DBContract.ChannelTable.TABLE_NAME, null, whereClause,
            whereArgs, null, null, null
        )
        if (cursor.count <= 0) {
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }

    override fun saveFeedsAndChannel(data: Pair<ArrayList<FeedItem>, ChannelItem>) {
        val (feeds, channel) = data
        val path = ImageSaver.saveImageToInternalStorage(channel.urlImage, channel.nameChannel)
        channel.pathImage = path.toString()
        insertChannel(channel)
        insertAllFeedsByChannel(feeds, channel.linkChannel)
    }


}