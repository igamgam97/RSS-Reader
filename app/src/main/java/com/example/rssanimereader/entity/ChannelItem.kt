package com.example.rssanimereader.entity


class ChannelItem(
    val linkChannel: String,
    val nameChannel: String,
    val urlImage: String,
    var pathImage: String?
) {
    constructor(
        linkChannel: String, nameChannel: String,
        urlImage: String
    ) : this(linkChannel, nameChannel, urlImage, null)
}