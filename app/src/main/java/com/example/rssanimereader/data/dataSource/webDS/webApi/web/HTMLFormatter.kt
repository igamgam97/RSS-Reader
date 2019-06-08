package com.example.rssanimereader.data.dataSource.webDS.webApi.web

interface HTMLFormatter<T> {
    fun generateHtml(item: T): String
}