package com.example.rssanimereader.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.model.dataSource.ChannelListDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class ChannelListViewModel(private val channelListDataSource: ChannelListDataSource) : ViewModel() {


    var channels = MutableLiveData<ArrayList<ChannelItem>>()
    var isAllFeedsButtonClicked = MutableLiveData<Boolean>()
    var isFavoriteFeedsButtonClicked = MutableLiveData<Boolean>()


    private val compositeDisposable = CompositeDisposable()

    init {
        getAllChannels()
    }

    fun getAllChannels() {

        val disposable = channelListDataSource.getChannels()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ data ->
                //data.add(0,ChannelItem("","all","",""))
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

    fun addChannel() {
        Log.d("bag","click")
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun onClickAllFeedsButton(){
        isAllFeedsButtonClicked.value = true
    }

    fun onClickFavoriteFeedsButton(){
        isFavoriteFeedsButtonClicked.value = true
    }

}