package com.example.rssanimereader.util.feedUtil.parser

import java.io.InputStream

interface Parser<T> {
    fun parse(input: InputStream): List<T>
}