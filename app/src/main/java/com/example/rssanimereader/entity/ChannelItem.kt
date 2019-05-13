package com.example.rssanimereader.entity

import android.graphics.Bitmap

class ChannelItem(
        val linkChannel: String,
        val nameChannel: String,
        val pathImage: String,
        var image: Bitmap?
) {
    constructor(
            linkChannel: String, nameChannel: String,
            pathImage: String
    ) : this(linkChannel, nameChannel, pathImage, null)
}