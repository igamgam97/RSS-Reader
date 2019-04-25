package com.example.rssanimereader.util.feedUtil


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase


class DatabaseAdapter(context: Context) {

    private val dbHelper: DatabaseHelper = DatabaseHelper(context.applicationContext)
    private var database: SQLiteDatabase? = null

    private val allEntries: Cursor
        get() {
            val columns = arrayOf(DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_TITLE, DatabaseHelper.COLUMN_DESCRIPTION, DatabaseHelper.COLUMN_LINK,DatabaseHelper.COLUMN_PUBDATE)
            return database!!.query(DatabaseHelper.TABLE, columns, null, null, null, null, null)
        }

    val users: List<FeedItem>
        get() {
            val users = ArrayList<FeedItem>()
            val cursor = allEntries
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID))
                    val title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE))
                    val description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION))
                    val link = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LINK))
                    val pubDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PUBDATE))
                    users.add(FeedItem(id, title, description, link, pubDate))
                } while (cursor.moveToNext())
            }
            cursor.close()
            return users
        }

    val count: Long
        get() = DatabaseUtils.queryNumEntries(database, DatabaseHelper.TABLE)

    fun open(): DatabaseAdapter {
        database = dbHelper.writableDatabase
        return this
    }

    fun close() {
        dbHelper.close()
    }

    fun getItemFeed(id: Long): FeedItem? {
        var user: FeedItem? = null
        val query = String.format("SELECT * FROM %s WHERE %s=?", DatabaseHelper.TABLE, DatabaseHelper.COLUMN_ID)
        val cursor = database!!.rawQuery(query, arrayOf(id.toString()))
        if (cursor.moveToFirst()) {
            val title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE))
            val description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION))
            val link = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LINK))
            val pubDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PUBDATE))
            user = FeedItem(id, title, description,link,pubDate)
        }
        cursor.close()
        return user
    }

    fun insert(item: FeedItem): Long {

        val cv = ContentValues()
        cv.put(DatabaseHelper.COLUMN_TITLE, item.itemTitle)
        cv.put(DatabaseHelper.COLUMN_DESCRIPTION, item.itemDesc)
        cv.put(DatabaseHelper.COLUMN_LINK, item.itemLink)
        cv.put(DatabaseHelper.COLUMN_PUBDATE, item.itemPubDate)

        return database!!.insert(DatabaseHelper.TABLE, null, cv)
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
        return database!!.update(DatabaseHelper.TABLE, cv, whereClause, null).toLong()
    }
}