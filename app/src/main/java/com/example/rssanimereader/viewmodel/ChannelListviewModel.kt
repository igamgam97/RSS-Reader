package com.example.rssanimereader.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.model.dataSource.ChannelListDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class ChannelListViewModel(private val channelListDataSource: ChannelListDataSource) : ViewModel() {


    var channels = MutableLiveData<ArrayList<ChannelItem>>()

    private val compositeDisposable = CompositeDisposable()

    init {
        getAllChannels()
    }

    fun getAllChannels() {

        val disposable = channelListDataSource.getChannels()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ data ->
                channels.value = data
            }
        compositeDisposable.add(disposable)
    }

    fun deleteChannel(nameChannel: String) {
        val disposable = channelListDataSource.deleteChannels(nameChannel)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ data ->
                channels.value = data
            }
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    //todo add dialogfragment and clear function
    /* fun clearFeedsByChannel(nameChannel: String){

     }*/
}