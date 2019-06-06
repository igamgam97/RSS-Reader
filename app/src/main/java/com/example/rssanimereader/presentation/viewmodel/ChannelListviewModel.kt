package com.example.rssanimereader.presentation.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.adapter.SwipeHandler
import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.model.dataSource.ChannelListDataSource
import com.example.rssanimereader.presentation.view.TypeOfButtonChannelListFragment
import com.example.rssanimereader.usecase.DeleteChannelsUseCase
import com.example.rssanimereader.usecase.GetChannelsUseCase
import com.example.rssanimereader.usecase.RetractDeleteBySwipeChannelUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class ChannelListViewModel(
    private val getChannelsUseCase: GetChannelsUseCase,
    private val deleteChannelsUseCase: DeleteChannelsUseCase,
    private val retractDeleteBySwipeChannelUseCase: RetractDeleteBySwipeChannelUseCase
) : ViewModel(), SwipeHandler {

    var channels = MutableLiveData<ArrayList<ChannelItem>>()
    val isTypeButtonClicked = MutableLiveData<TypeOfButtonChannelListFragment>()
    var positionOnDelete = 0
    lateinit var tempItem: Pair<Int, ChannelItem>
    private val compositeDisposable = CompositeDisposable()

    init {
        getAllChannels()
    }

    fun getAllChannels() {
        val disposable = getChannelsUseCase()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { data ->
                channels.value = data
            }
        compositeDisposable.add(disposable)
    }

    fun deleteChannel(nameChannel: String) {
        val disposable = deleteChannelsUseCase(nameChannel)
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
        val disposable = retractDeleteBySwipeChannelUseCase(channelItem)
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