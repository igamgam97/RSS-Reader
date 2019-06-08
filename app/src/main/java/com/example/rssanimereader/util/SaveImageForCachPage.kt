package com.example.rssanimereader.util

import android.net.Uri
import com.example.rssanimereader.data.dataSource.webDS.webApi.web.ImageSaver
import java.util.regex.Pattern


object SaveImageForCashPage {
    operator fun invoke(itemDesc: String,name:String) : Uri? {
        val link = getURLFromTagImage(itemDesc)
        return getURLFromTagImage(itemDesc)?.let {
            ImageSaver.saveImageToInternalStorage(it,name)
        }
    }

    /*fun convertRemoteURLToLocal(feedItem: FeedItem) : Uri {
        val remotePathImage = getURLFromTagImage(feedItem.itemDesc)

        val streamFromURLLoader = StreamFromURLLoader()

        streamFromURLLoader(remotePathImage).inputStream.use {
            return ImageSaver.saveImageToInternalStorage(BitmapFactory.decodeStream(it),feedItem.itemTitle)
        }*/


}

fun getURLFromTagImage(text: String): String? {
    val pattern = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>")

    val m = pattern.matcher(text)

    return if (m.find()) {
        m.group(1)
    } else {
        null
    }
}


private object DetectHtml {
    // adapted from post by Phil Haack and modified to match better
    const val tagStart = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)\\>"
    const val tagEnd = "\\</\\w+\\>"
    const val tagSelfClosing = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)/\\>"
    const val htmlEntity = "&[a-zA-Z][a-zA-Z0-9]+;"
    val htmlPattern: Pattern = Pattern.compile(
        "($tagStart.*$tagEnd)|($tagSelfClosing)|($htmlEntity)",
        Pattern.DOTALL
    )


    operator fun invoke(s: String?): Boolean {
        var ret = false
        if (s != null) {
            ret = htmlPattern.matcher(s).find()
        }
        return ret
    }


}
