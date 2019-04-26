package com.example.rssanimereader.util.feedUtil

interface DBInsertAllFeeds<T> {
    fun insertAll(items: List<T>)

    fun open(): DBInsertAllFeeds<T>

    fun close()
}