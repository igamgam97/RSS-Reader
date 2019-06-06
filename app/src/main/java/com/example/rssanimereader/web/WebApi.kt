package com.example.rssanimereader.web

import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.entity.FeedItem
import java.io.InputStream

class WebApi(
    private val remoteDataParser: RemoteDataParser,
    private val imageSaver: NewImageSaver
) {


    fun getFeedsAndChannelFromWeb(urlPath: String): Pair<ArrayList<FeedItem>, ChannelItem> {
        val streamFromURLLoader = StreamFromURLLoader()
        streamFromURLLoader(urlPath).inputStream.use {
            /* val data = RSSRemoteDataParser().parse(it,urlPath)*/
            val data = remoteDataParser.parse(it, urlPath)
            return data
        }
    }

    fun saveImageFromWeb(urlPath: String,name: String){
        imageSaver.saveImageToInternalStorage(urlPath,name)
    }


}

interface RemoteDataParser {
    fun parse(input: InputStream, source: String): Pair<ArrayList<FeedItem>, ChannelItem>
}