package com.example.rssanimereader.data.web.parser

interface HTMLFormatter<T> {
    fun generateHtml(item: T): String
}