package com.example.rssanimereader.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import com.example.rssanimereader.R
import com.example.rssanimereader.databinding.ActivityMainLayoutBinding
import com.example.rssanimereader.viewmodel.CommunicateViewModel


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainLayoutBinding
    lateinit var viewModel: CommunicateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

      /*  when(intent?.action) {
             Intent.ACTION_VIEW -> {
                handle(intent)
            }
        }
*/
        Log.d("bag", "onCreate")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_layout)
        viewModel = ViewModelProviders.of(this).get(CommunicateViewModel::class.java)
        viewModel.listOfTypeFragment.observe(this, Observer {
            it?.let { stack ->
                setFragment(stack.peek())
            }
        })
        binding.mainViewModel = viewModel

        /*  delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES*/

    }



    override fun onResume() {
        super.onResume()
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val nightModeEnabled = prefs.getBoolean("NIGHT_MODE_VALUE", false)
        if (nightModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }


    private fun setFragment(tagFragment: ListOfTypeFragment) {

        val currentFragment = supportFragmentManager
            .findFragmentByTag(tagFragment.toString())
        if (currentFragment == null) {
            val fragment = when (tagFragment) {
                ListOfTypeFragment.FeedListFragment -> {
                    FeedListFragment()
                }
                ListOfTypeFragment.SearchFragment -> SearchFragment()
                ListOfTypeFragment.FeedFragment -> {
                    FeedFragment()
                }
                ListOfTypeFragment.ChannelListFragment -> ChannelListFragment()
                ListOfTypeFragment.SettingsFragment -> SettingsFragment()
            }
            openFragment(fragment, tagFragment.toString())
        } else {
            openFragment(currentFragment, tagFragment.toString())
        }
    }

    private fun openFragment(fragment: Fragment, nameFragment: String) {
        fragment.retainInstance = true
        supportFragmentManager.beginTransaction()
            .replace(R.id.frag_container, fragment, nameFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        viewModel.mListOfTypeFragment.value!!.pop()!!
        if (viewModel.mListOfTypeFragment.value!!.isNotEmpty()) {
            val state = viewModel.mListOfTypeFragment.value!!.peek()
            setFragment(state)
        } else {
            finish()
        }
    }
    private fun handle(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
        }
    }

}


