package com.example.rssanimereader.adapter

interface SwipeHandler {

    fun onItemSwipedLeft(position: Int)

    fun onItemSwipedRight(position: Int)
}