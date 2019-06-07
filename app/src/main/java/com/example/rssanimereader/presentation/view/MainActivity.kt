package com.example.rssanimereader.presentation.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import com.example.rssanimereader.R
import com.example.rssanimereader.databinding.ActivityMainLayoutBinding
import com.example.rssanimereader.peridic_feed_manager.PeriodicDownloadFeedsWorkerUtils
import com.example.rssanimereader.presentation.view_model.CommunicateViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainLayoutBinding
    private lateinit var viewModel: CommunicateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applySettings()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_layout)
        viewModel = ViewModelProviders.of(this).get(CommunicateViewModel::class.java)
        openApplicationFromDeepLink()
        viewModel.listOfTypeFragment.observe(this, Observer {
            it?.let { fragmentName ->
                setFragment(fragmentName)
            }
        })
        binding.mainViewModel = viewModel

        Log.d("bag", "tag")
        PeriodicDownloadFeedsWorkerUtils.startPeriodicDownloadFeedsWorker()

    }

    //todo добавить открытие по ссылке
    private fun openApplicationFromDeepLink() {
        /*    val action: String? = intent?.action*/
        val data = intent?.data
        data?.let {
            viewModel.onFeedListFragmentStateFromSearchFragment(it.toString())
        }
    }

    private fun applySettings() {
        if (intent.getBooleanExtra(SettingsFragment.SETTING_FRAGMENT, false)) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(this)
            val nightModeEnabled = prefs.getBoolean("NIGHT_MODE_VALUE", false)
            if (nightModeEnabled) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }


    private fun setFragment(tagFragment: ListOfTypeFragment) {

        val currentFragment = supportFragmentManager
            .findFragmentByTag(tagFragment.toString())
        if (currentFragment == null) {
            val fragment = when (tagFragment) {
                ListOfTypeFragment.FeedListFragmentFromAddChannelDialogFragment
                    , ListOfTypeFragment.FeedListFragmentFromChannelListFragment -> {
                    FeedListFragment()
                }
                ListOfTypeFragment.FeedFragment -> {
                    Log.d("bag", "createListOfFragment")
                    FeedFragment()
                }
                ListOfTypeFragment.ChannelListFragment -> ChannelListFragment()
                ListOfTypeFragment.SettingsFragment -> SettingsFragment()
            }
            openFragment(fragment, tagFragment)
        } else {
            replaceFragment(currentFragment, tagFragment)
        }
    }


    private fun replaceFragment(fragment: Fragment, tagFragment: ListOfTypeFragment) {
        if (tagFragment == ListOfTypeFragment.FeedListFragmentFromChannelListFragment ||
            tagFragment == ListOfTypeFragment.FeedListFragmentFromAddChannelDialogFragment
        ) {
            binding.navigation.menu.findItem(R.id.app_bar_feeds).isChecked = true
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.frag_container, fragment, tagFragment.toString())
            .addToBackStack(null)
            .commit()
        when (tagFragment) {
            ListOfTypeFragment.FeedListFragmentFromChannelListFragment -> {
                (fragment as FeedListFragment).setParamsFromChannelListFragment(viewModel.targetChannel)
            }
            ListOfTypeFragment.FeedListFragmentFromAddChannelDialogFragment -> {
                (fragment as FeedListFragment).setParamsFromAddChannelDealogFragment(viewModel.searchChannel)
            }
            ListOfTypeFragment.FeedFragment -> {
                (fragment as FeedFragment).setParams(viewModel.selectedFeed)
            }
            ListOfTypeFragment.ChannelListFragment -> (fragment as ChannelListFragment).setParams()
        }
    }

    private fun openFragment(fragment: Fragment, tagFragment: ListOfTypeFragment) {
        if (tagFragment == ListOfTypeFragment.FeedListFragmentFromChannelListFragment ||
            tagFragment == ListOfTypeFragment.FeedListFragmentFromAddChannelDialogFragment
        ) {
            binding.navigation.menu.findItem(R.id.app_bar_feeds).isChecked = true
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.frag_container, fragment, tagFragment.toString())
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d("bag", viewModel.mListOfTypeFragment.value.toString())
        /*if (viewModel.mListOfTypeFragment.value == ListOfTypeFragment.FeedListFragment){
            Log.d("bag",viewModel.mListOfTypeFragment.value.toString())
            finish()
        }*/
        changeSelectedTabIcon()
    }

    private fun changeSelectedTabIcon() {
        val displayedFragments = supportFragmentManager.fragments
        if (displayedFragments.isNotEmpty()) {
            val itemID = when (supportFragmentManager.fragments.first()) {
                is ChannelListFragment -> R.id.app_bar_channels
                is FeedListFragment, is FeedFragment -> R.id.app_bar_feeds
                else -> R.id.app_bar_settings
            }
            binding.navigation.menu.findItem(itemID).isChecked = true
        }
    }

}


