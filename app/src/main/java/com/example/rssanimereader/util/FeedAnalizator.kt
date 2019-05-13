package com.example.rssanimereader.util

import com.example.rssanimereader.entity.FeedItem
import java.util.regex.Pattern



object FeedAnalizator{
    operator fun invoke(feedItem:FeedItem){
        if (DetectHtml(feedItem.itemDesc)){

        }
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