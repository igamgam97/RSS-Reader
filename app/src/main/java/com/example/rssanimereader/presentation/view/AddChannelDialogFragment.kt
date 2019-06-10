package com.example.rssanimereader.presentation.view

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.rssanimereader.databinding.AddChanneDialogFragmentlBinding
import com.example.rssanimereader.di.Injection
import com.example.rssanimereader.presentation.view_model.AddChannelViewModel
import com.example.rssanimereader.presentation.view_model.CommunicateViewModel


class AddChannelDialogFragment : DialogFragment() {
    private lateinit var addChannelViewModel: AddChannelViewModel
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d("bag", "there")
        val binding = AddChanneDialogFragmentlBinding.inflate(
            LayoutInflater.from(context), null, false
        )
        addChannelViewModel = Injection.provideAddChannelViewModel(this)
        addChannelViewModel.isClickSearchButton.observe(this, Observer<Boolean> {
            onSearchClick()
        })
        binding.addChannelViewModel = this@AddChannelDialogFragment.addChannelViewModel
        return AlertDialog.Builder(requireContext())
            .setTitle("Search channel")
            .setMessage("PleaseChooseChannel")
            .setView(binding.root)
            .setNegativeButton(CLOSE) { _, _ -> dismiss() }
            .setPositiveButton(SEARCH_CHANNEL, null)
            .create()
    }

    override fun onResume() {
        super.onResume()
        val alertDialog = dialog as AlertDialog
        val okButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        okButton.setOnClickListener { addChannelViewModel.searchChannel() }
        addChannelViewModel.statusError.observe(this, Observer {
            if (it == false) {
                alertDialog.setMessage("Incorrect url")
            }

        })
    }

    private fun onSearchClick() {
        val communicateViewModel = ViewModelProviders.of(activity!!).get(CommunicateViewModel::class.java)
        addChannelViewModel.targetChannel.get()
            ?.let { communicateViewModel.onFeedListFragmentStateFromSearchFragment(it) }
        dismiss()
    }

    private companion object {
        const val SEARCH_CHANNEL = "search"
        const val CLOSE = "close"
    }
}