package com.example.rssanimereader.presentation.view_model

import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.R
import com.example.rssanimereader.domain.entity.FeedItem
import com.example.rssanimereader.presentation.view.ListOfTypeFragment

//todo change -> router
class CommunicateViewModel : ViewModel() {
    val mListOfTypeFragment: MutableLiveData<ListOfTypeFragment> = MutableLiveData()

    val listOfTypeFragment: LiveData<ListOfTypeFragment>
        get() = mListOfTypeFragment

    var targetChannel = ""
    var searchChannel = ""

    var selectedFeed: FeedItem? = null

    init {
        mListOfTypeFragment.value = ListOfTypeFragment.FeedListFragmentFromChannelListFragment
    }

    //todo add put logic
    fun onFeedListFragmentState(linkChannel: String) {
        targetChannel = linkChannel
        mListOfTypeFragment.value = ListOfTypeFragment.FeedListFragmentFromChannelListFragment
    }

    fun onFeedListFragmentStateFromSearchFragment(linkChannel: String) {
        searchChannel = linkChannel
        mListOfTypeFragment.value = ListOfTypeFragment.FeedListFragmentFromAddChannelDialogFragment
    }


    fun onFeedFragmentState(feedItem: FeedItem) {
        selectedFeed = feedItem
        mListOfTypeFragment.value = ListOfTypeFragment.FeedFragment
    }

    private fun onSettingsFragmentState() {
        mListOfTypeFragment.value = ListOfTypeFragment.SettingsFragment
    }

    private fun onChannelListFragmentState() {
        mListOfTypeFragment.value = ListOfTypeFragment.ChannelListFragment
    }


    fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_channels -> onChannelListFragmentState()
            R.id.app_bar_settings -> onSettingsFragmentState()
            R.id.app_bar_feeds -> mListOfTypeFragment.value = ListOfTypeFragment.FeedListFragmentFromChannelListFragment
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
