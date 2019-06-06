package com.example.rssanimereader.model.dataSource.webDS.webApi.web

interface HTMLFormatter<T> {
    fun generateHtml(item: T): String
}