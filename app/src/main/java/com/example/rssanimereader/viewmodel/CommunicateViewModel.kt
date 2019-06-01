package com.example.rssanimereader.viewmodel

import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.R
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.view.ListOfTypeFragment

//todo change -> router
class CommunicateViewModel : ViewModel() {
    val mListOfTypeFragment: MutableLiveData<ListOfTypeFragment> = MutableLiveData()

    val listOfTypeFragment: LiveData<ListOfTypeFragment>
        get() = mListOfTypeFragment

    val targetChannel = MutableLiveData<String>()
    val searchChannel = MutableLiveData<String>()

    lateinit var selectedFeed: FeedItem

    init {
        mListOfTypeFragment.value = ListOfTypeFragment.FeedListFragment
    }

    //todo add put logic
    fun onFeedListFragmentState(linkChannel: String) {
        targetChannel.value = linkChannel
        mListOfTypeFragment.value = ListOfTypeFragment.FeedListFragment
    }

    fun onFeedListFragmentStateFromSearchFragment(linkChannel:String){
        searchChannel.value = linkChannel
        mListOfTypeFragment.value = ListOfTypeFragment.FeedListFragment
    }


    fun onFeedFragmentState(feedItem: FeedItem) {
        selectedFeed = feedItem
        mListOfTypeFragment.value = ListOfTypeFragment.FeedFragment
    }

    fun onSettingsFragmentState() {
        mListOfTypeFragment.value = ListOfTypeFragment.SettingsFragment
    }

    fun onChannelListFragmentState() {
        mListOfTypeFragment.value = ListOfTypeFragment.ChannelListFragment
    }


    fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.app_bar_channels -> onChannelListFragmentState()
            R.id.app_bar_settings-> onSettingsFragmentState()
            R.id.app_bar_feeds ->  mListOfTypeFragment.value = ListOfTypeFragment.FeedListFragment
        }

        return true
    }
    //todo improve get in Factory (null) private set public get
    //todo application context работает и в factory
    //todo add delete feeds in recyclerView
    //todo timber.d
    //todo rename fragment vi
    // todo перехватывать ошибку скачивания и брать из офлайна
}
