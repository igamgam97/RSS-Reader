package com.example.rssanimereader.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.rssanimereader.databinding.FragmentSettingsBinding
import com.example.rssanimereader.di.Injection
import com.example.rssanimereader.viewmodel.SearchViewModel
import com.example.rssanimereader.viewmodel.SettingsViewModel


/**
 * A simple [Fragment] subclass.
 *
 */
class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel

    private lateinit var settingsViewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsViewModelFactory = Injection.provideViewModelFactory(this)
        settingsViewModel = ViewModelProviders.of(this, settingsViewModelFactory)
            .get(SettingsViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSettingsBinding.inflate(inflater, container, false).apply {
        settingsViewModel = this@SettingsFragment.settingsViewModel
    }.root


}
