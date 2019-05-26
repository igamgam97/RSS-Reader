package com.example.rssanimereader.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.rssanimereader.databinding.FragmentSeerchBinding
import com.example.rssanimereader.di.Injection
import com.example.rssanimereader.viewmodel.CommunicateViewModel
import com.example.rssanimereader.viewmodel.SearchViewModel


class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel

    private lateinit var searchViewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchViewModel = Injection.provideSearchViewModel(this)


        searchViewModel.isClickSearchButton.observe(this, Observer<Boolean> {
            onSearchClick()
        })
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ) = FragmentSeerchBinding.inflate(inflater, container, false).apply {
        searchViewModel = this@SearchFragment.searchViewModel
    }.root

    fun onSearchClick() {
        val communicateViewModel = ViewModelProviders.of(activity!!).get(CommunicateViewModel::class.java)
        communicateViewModel.onFeedListFramgentState()
        communicateViewModel.targetChannel.value = searchViewModel.targetChannel.get()!!
    }

}
