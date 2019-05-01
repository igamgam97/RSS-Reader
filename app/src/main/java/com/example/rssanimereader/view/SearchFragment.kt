package com.example.rssanimereader.view


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.rssanimereader.R
import com.example.rssanimereader.databinding.FragmentSeerchBinding
import com.example.rssanimereader.viewmodel.CommunicateViewModel
import com.example.rssanimereader.viewmodel.SearchViewModel


class SearchFragment : Fragment() {

    lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewModel = ViewModelProviders.of(activity!!).get(SearchViewModel::class.java)
        searchViewModel.isClickSearchButton.observe(this, Observer<Boolean>{
            onSearchClick()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = (DataBindingUtil.inflate(
            inflater, R.layout.fragment_seerch, container, false)
                as FragmentSeerchBinding)
        // don't work with apply and with
        binding.searchViewModel = searchViewModel
        return binding.root
    }

    fun onSearchClick(){
        val communicateViewModel = ViewModelProviders.of(activity!!).get(CommunicateViewModel::class.java)
        communicateViewModel.onFeedListFramgentState()
        communicateViewModel.targetChannel =searchViewModel.targetChannel.get()!!
    }

}
