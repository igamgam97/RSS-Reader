package com.example.rssanimereader.util

import com.example.rssanimereader.entity.FeedItem
import java.util.regex.Pattern


object FeedAnalizator {
    operator fun invoke(feedList: ArrayList<FeedItem>) {
        if (DetectHtml(feedList[0].itemDesc)) {
            feedList.forEach {
                it.itemDesc = HTMLFeedFormatter().generateHtml(it)
            }
        }
    }

    fun convertRemoteURLToLocal(feedItem: FeedItem) {
        val pathImage = getURLFromTagImage(feedItem.itemDesc)
        val streamFromURLLoader = StreamFromURLLoader()
        streamFromURLLoader(pathImage!!)

    }

    fun getURLFromTagImage(text: String): String? {
        val pattern = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>")

        val m = pattern.matcher(text)

        return m.group(1)
    }


    private object DetectHtml {
        // adapted from post by Phil Haack and modified to match better
        const val tagStart = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)\\>"
        const val tagEnd = "\\</\\w+\\>"
        const val tagSelfClosing = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)/\\>"
        const val htmlEntity = "&[a-zA-Z][a-zA-Z0-9]+;"
        val htmlPattern = Pattern.compile(
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
}