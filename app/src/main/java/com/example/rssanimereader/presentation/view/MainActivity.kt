package com.example.rssanimereader.presentation.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import com.example.rssanimereader.R
import com.example.rssanimereader.databinding.ActivityMainLayoutBinding
import com.example.rssanimereader.peridic_feed_manager.PeriodicDownloadFeedsWorkerUtils
import com.example.rssanimereader.presentation.view.contracts.BaseFragment
import com.example.rssanimereader.presentation.view_model.CommunicateViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainLayoutBinding
    private lateinit var viewModel: CommunicateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applySettings()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_layout)
        viewModel = ViewModelProviders.of(this).get(CommunicateViewModel::class.java)
        viewModel.listOfTypeFragment.observe(this, Observer {
            it?.let(::setFragment)
        })
        binding.mainViewModel = viewModel

        Log.d("bag", "tag")
        PeriodicDownloadFeedsWorkerUtils.startPeriodicDownloadFeedsWorker()

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        openApplicationFromDeepLink()
    }

    private fun openApplicationFromDeepLink() {
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
            replaceFragment(fragment, tagFragment)
        } else {
            replaceFragment(currentFragment as BaseFragment, tagFragment)
            currentFragment.setData()
        }
    }


    private fun replaceFragment(fragment: BaseFragment, tagFragment: ListOfTypeFragment) {
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
        if (supportFragmentManager.backStackEntryCount == EMPTY_STACK) {
            finish()
        }
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

    private companion object {
        const val EMPTY_STACK = 0
    }
}


