package com.example.rssanimereader.presentation.view

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.rssanimereader.databinding.DialogFragmentAddChannelBinding
import com.example.rssanimereader.di.Injection
import com.example.rssanimereader.presentation.viewmodel.CommunicateViewModel
import com.example.rssanimereader.presentation.viewmodel.SearchViewModel




class AddChannelDialogFragment :DialogFragment(){
    lateinit var searchViewModel:SearchViewModel
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d("bag","there")
        val binding = DialogFragmentAddChannelBinding.inflate(
            LayoutInflater.from(context), null, false
        )
        searchViewModel = Injection.provideAddChannelViewModel(this)
        searchViewModel.isClickSearchButton.observe(this, Observer<Boolean> {
            onSearchClick()
        })
        binding.searchViewModel = this@AddChannelDialogFragment.searchViewModel
        return AlertDialog.Builder(requireContext())
            .setTitle("Search channel")
            .setMessage("PleaseChooseChannel")
            .setView(binding.root)
            .setNegativeButton("negative") {_,_ ->  dismiss()}
            .setPositiveButton("positive",null)
            .create()
    }

    override fun onResume() {
        super.onResume()
        val alertDialog = dialog as AlertDialog?
        val okButton = alertDialog!!.getButton(AlertDialog.BUTTON_POSITIVE)
        okButton.setOnClickListener{searchViewModel.searchChannel()}
        searchViewModel.statusError.observe(this, Observer {
            if (it==false) {
                alertDialog.setMessage("Incorrect url")
            }

        })
    }

    fun onSearchClick() {
        val communicateViewModel = ViewModelProviders.of(activity!!).get(CommunicateViewModel::class.java)
        searchViewModel.targetChannel.get()?.let { communicateViewModel.onFeedListFragmentStateFromSearchFragment(it) }
        dismiss()
    }
}