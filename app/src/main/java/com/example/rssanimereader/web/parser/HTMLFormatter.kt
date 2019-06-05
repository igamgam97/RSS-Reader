package com.example.rssanimereader.web.parser

interface HTMLFormatter<T> {
    fun generateHtml(item: T): String
}