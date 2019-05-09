package com.example.rssanimereader.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.rssanimereader.R
import com.example.rssanimereader.databinding.ActivityMainLayoutBinding
import com.example.rssanimereader.viewmodel.CommunicateViewModel
import com.example.rssanimereader.viewmodel.EnumFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainLayoutBinding
    lateinit var viewModel: CommunicateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_layout)
        viewModel = ViewModelProviders.of(this).get(CommunicateViewModel::class.java)
        viewModel.enumFragment.observe(this, Observer {
            it?.let {
                setFragment(it.peek())
            }
        })
        binding.mainViewModel = viewModel
    }


    private fun setFragment(tagFragment: EnumFragment) {

        val currentFragment = supportFragmentManager
            .findFragmentByTag(tagFragment.toString())
        if (currentFragment == null) {
            val fragment = when (tagFragment) {
                EnumFragment.FeedListFragment -> FeedListFragment()
                EnumFragment.SearchFragment -> SearchFragment()
                EnumFragment.FeedFragment -> FeedFragment()
                EnumFragment.ChannelListFragment -> ChannelListFragment()
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
        viewModel.mEnumFragment.value!!.pop()!!
        if (viewModel.mEnumFragment.value!!.isNotEmpty()) {
            val state = viewModel.mEnumFragment.value!!.peek()
            setFragment(state)
        } else {
            finish()
        }
    }


}


