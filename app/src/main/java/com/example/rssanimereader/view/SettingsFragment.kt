package com.example.rssanimereader.view


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.rssanimereader.R
import com.example.rssanimereader.databinding.FragmentSettingsBinding
import com.example.rssanimereader.di.Injection
import com.example.rssanimereader.viewmodel.SettingsViewModel


/**
 * A simple [Fragment] subclass.
 *
 */
class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsViewModel = Injection.provideSettingsViewModel(this)
        settingsViewModel.settingsNightMode.observe(this, Observer {
            changeTheme()
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSettingsBinding.inflate(inflater, container, false).apply {
        settingsViewModel = this@SettingsFragment.settingsViewModel
    }.root


    private fun changeTheme() {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra(SETTING_FRAGMENT, true)
        startActivity(intent)
        activity?.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
        activity?.finish()
    }

    companion object {
        const val SETTING_FRAGMENT = "settings_fragment"
    }
}
