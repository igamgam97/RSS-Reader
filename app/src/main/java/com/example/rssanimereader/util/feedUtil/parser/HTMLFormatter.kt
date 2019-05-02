package com.example.rssanimereader.util.feedUtil.parser

interface HTMLFormatter<T> {
    fun generateHtml(item: T): String
}