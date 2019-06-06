package com.example.rssanimereader.domain.entity


class ChannelItem(
    val linkChannel: String,
    val nameChannel: String,
    val urlImage: String,
    var pathImage: String? = null
)