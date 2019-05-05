package com.example.rssanimereader.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.model.dataSource.ChannelListDataSource

class ChannelListViewModel(channelListDataSource: ChannelListDataSource) : ViewModel() {


    var channels = MutableLiveData<ArrayList<ChannelItem>>()



    init {
        channelListDataSource.getChannels {data ->
            run {
                channels.value = data
            }
        }
    }


}