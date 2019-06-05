package com.example.rssanimereader.presentation.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.adapter.SwipeHandler
import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.model.dataSource.ChannelListDataSource
import com.example.rssanimereader.presentation.view.TypeOfButtonChannelListFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class ChannelListViewModel(private val channelListDataSource: ChannelListDataSource) : ViewModel(), SwipeHandler {

    var channels = MutableLiveData<ArrayList<ChannelItem>>()
    val isTypeButtonClicked = MutableLiveData<TypeOfButtonChannelListFragment>()
    var positionOnDelete = 0
    lateinit var tempItem: Pair<Int, ChannelItem>
    private val compositeDisposable = CompositeDisposable()

    init {
        getAllChannels()
    }

    fun getAllChannels() {
        val disposable = channelListDataSource.getChannels()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { data ->
                channels.value = data
            }
        compositeDisposable.add(disposable)
    }

    fun deleteChannel(nameChannel: String) {
        val disposable = channelListDataSource.deleteChannels(nameChannel)
            .andThen(channelListDataSource.getChannels())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { data ->
                channels.value = data
            }
        compositeDisposable.add(disposable)
    }

    fun addChannel() {
        isTypeButtonClicked.value= TypeOfButtonChannelListFragment.ShowAddChannelDialogFragment
    }

    fun onClickAllFeedsButton() {
        isTypeButtonClicked.value= TypeOfButtonChannelListFragment.ShowAllFeeds
    }

    fun onClickFavoriteFeedsButton() {
        isTypeButtonClicked.value= TypeOfButtonChannelListFragment.ShowFavoriteFeeds
    }

    fun retractSaveChannel(channelItem: ChannelItem) {
        val disposable = channelListDataSource.retractSaveChannel(channelItem)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {}
        compositeDisposable.add(disposable)
    }

    override fun onItemSwipedLeft(position: Int) {
        positionOnDelete = position
        saveAndRemoveItem(position)
        isTypeButtonClicked.value = TypeOfButtonChannelListFragment.SwipeLeft

    }

    override fun onItemSwipedRight(position: Int) {
        positionOnDelete = position
        saveAndRemoveItem(position)
        isTypeButtonClicked.value = TypeOfButtonChannelListFragment.SwipeRight
    }

    private fun saveAndRemoveItem(position: Int) {
        tempItem = Pair(position, channels.value!![position])
        deleteChannel(channels.value!![position].linkChannel)
    }


    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}