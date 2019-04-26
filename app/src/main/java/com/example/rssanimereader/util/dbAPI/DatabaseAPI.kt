package com.example.rssanimereader.util.dbAPI


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.entity.TitleFeedItem
import com.example.rssanimereader.util.feedUtil.DBInsertAllFeeds


class DatabaseAPI(context: Context) : DBInsertAllFeeds<FeedItem> {

    private val dbHelper: DatabaseHelper =
        DatabaseHelper(context.applicationContext)
    private var database: SQLiteDatabase? = null

    private val allEntries: Cursor
        get() {
            val columns = arrayOf(
                DatabaseHelper.COLUMN_ID,
                DatabaseHelper.COLUMN_TITLE, DatabaseHelper.COLUMN_DESCRIPTION,
                DatabaseHelper.COLUMN_LINK, DatabaseHelper.COLUMN_PUBDATE,
                DatabaseHelper.COLUMN_SOURCE
            )
            return database!!.query(DatabaseHelper.TABLE, columns, null, null, null, null, null)
        }

    val items: List<FeedItem>
        get() {
            val items = ArrayList<FeedItem>()
            val cursor = allEntries
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID))
                    val title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE))
                    val description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION))
                    val link = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LINK))
                    val pubDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PUBDATE))
                    val source = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SOURCE))
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
    val itemsTitle: List<TitleFeedItem>
        get() {
            val items = ArrayList<TitleFeedItem>()
            val cursor = allEntries
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID))
                    val title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE))
                    items.add(TitleFeedItem(id, title))
                } while (cursor.moveToNext())
            }
            cursor.close()
            return items
        }
    val count: Long
        get() = DatabaseUtils.queryNumEntries(database, DatabaseHelper.TABLE)

    override fun open(): DatabaseAPI {
        database = dbHelper.writableDatabase
        return this
    }

    override fun close() {
        dbHelper.close()
    }

    fun getItemFeed(id: Long): FeedItem? {
        var item: FeedItem? = null
        val query = String.format("SELECT * FROM %s WHERE %s=?", DatabaseHelper.TABLE, DatabaseHelper.COLUMN_ID)
        val cursor = database!!.rawQuery(query, arrayOf(id.toString()))
        if (cursor.moveToFirst()) {
            val title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE))
            val description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION))
            val link = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LINK))
            val pubDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PUBDATE))
            val source = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SOURCE))
            item =
                FeedItem(id, title, description, link, pubDate, source)
        }
        cursor.close()
        return item
    }

    fun insert(item: FeedItem): Long {
        val cv = ContentValues()
        cv.put(DatabaseHelper.COLUMN_TITLE, item.itemTitle)
        cv.put(DatabaseHelper.COLUMN_DESCRIPTION, item.itemDesc)
        cv.put(DatabaseHelper.COLUMN_LINK, item.itemLink)
        cv.put(DatabaseHelper.COLUMN_PUBDATE, item.itemPubDate)
        cv.put(DatabaseHelper.COLUMN_SOURCE, item.source)
        return database!!.insert(DatabaseHelper.TABLE, null, cv)
    }

    override fun insertAll(items: List<FeedItem>) {
        database?.beginTransaction()
        items.forEach {
            val cv = ContentValues()
            cv.put(DatabaseHelper.COLUMN_TITLE, it.itemTitle)
            cv.put(DatabaseHelper.COLUMN_DESCRIPTION, it.itemDesc)
            cv.put(DatabaseHelper.COLUMN_LINK, it.itemLink)
            cv.put(DatabaseHelper.COLUMN_PUBDATE, it.itemPubDate)
            cv.put(DatabaseHelper.COLUMN_SOURCE, it.source)
            database!!.insert(DatabaseHelper.TABLE, null, cv)
        }
        database?.setTransactionSuccessful()
        database?.endTransaction()
    }

    fun delete(itemFeedId: Long): Long {

        val whereClause = "_id = ?"
        val whereArgs = arrayOf(itemFeedId.toString())
        return database!!.delete(DatabaseHelper.TABLE, whereClause, whereArgs).toLong()
    }

    fun update(item: FeedItem): Long {

        val whereClause = DatabaseHelper.COLUMN_ID + "=" + item.id.toString()
        val cv = ContentValues()
        cv.put(DatabaseHelper.COLUMN_TITLE, item.itemTitle)
        cv.put(DatabaseHelper.COLUMN_DESCRIPTION, item.itemDesc)
        cv.put(DatabaseHelper.COLUMN_LINK, item.itemLink)
        cv.put(DatabaseHelper.COLUMN_PUBDATE, item.itemPubDate)
        cv.put(DatabaseHelper.COLUMN_SOURCE, item.source)
        return database!!.update(DatabaseHelper.TABLE, cv, whereClause, null).toLong()
    }
}