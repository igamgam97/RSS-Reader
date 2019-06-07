package com.example.rssanimereader.presentation.view


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.rssanimereader.R
import com.example.rssanimereader.databinding.SettingsFragmentBinding
import com.example.rssanimereader.di.Injection
import com.example.rssanimereader.presentation.view.contracts.BaseFragment
import com.example.rssanimereader.presentation.view_model.SettingsViewModel




/**
 * A simple [Fragment] subclass.
 *
 */
class SettingsFragment : BaseFragment() {

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
    ) = SettingsFragmentBinding.inflate(inflater, container, false).apply {
        settingsViewModel = this@SettingsFragment.settingsViewModel
    }.root



    private fun changeTheme() {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra(SETTING_FRAGMENT, true)
        startActivity(intent)
        activity?.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
        activity?.finish()
    }

    override fun setData() {

    }

    companion object {
        const val SETTING_FRAGMENT = "settings_fragment"
    }
}
