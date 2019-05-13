package com.example.rssanimereader.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.model.dataSource.ChannelListDataSource

class ChannelListViewModel(private val channelListDataSource: ChannelListDataSource) : ViewModel() {


    var channels = MutableLiveData<ArrayList<ChannelItem>>()


    init {
        getAllChannels()
    }

    fun getAllChannels() {
        channelListDataSource.getChannels { data ->
            run {
                channels.value = data

            }
        }
    }

    fun deleteChannel(nameChannel: String) {
        channelListDataSource.deleteChannels(nameChannel) { data ->
            channels.value = data
        }
    }

    //todo add dialogfragment and clear function
    /* fun clearFeedsByChannel(nameChannel: String){

     }*/
}