package com.example.rssanimereader

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentTransaction
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import androidx.preference.PreferenceManager


class SettingsPrefActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            .replace(R.id.settings, SettingsFragment())
            .commit()
    }

    override fun onResume() {
        super.onResume()
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val enabled = prefs.getBoolean("key_night_theme", false)
        Log.d("bag",enabled.toString())
        if (enabled){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

    }


    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root, rootKey)

        }

    }

    class ThemeSettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.pref_main, rootKey)
            val switch = findPreference<SwitchPreference>("key_night_theme")
            switch!!.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, _ ->
                if (switch.isChecked) {
                    Log.d("bag", "click")
                   /* AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)*/
                    switch.isChecked = false
                    startActivity(SettingsPrefActivity.createSettingsIntent(activity as Context))
                    activity?.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
                    activity?.finish()
                } else {
                 /*   AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)*/
                    // Unchecked the switch programmatically
                    switch.isChecked = true
                    startActivity(SettingsPrefActivity.createSettingsIntent(activity as Context))
                    activity?.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
                    activity?.finish()
                }
                false
            }
        }
    }

    override fun onBackPressed() {
        //todo change logic
        super.onBackPressed()
    }

    companion object {
        fun createSettingsIntent(context: Context): Intent {
            val intent = Intent(context, SettingsPrefActivity::class.java)
            intent.putExtra(SETTING_FRAGMENT, true)
            return intent
        }

        private val SETTING_FRAGMENT = "settings_fragment"
        private val SETTINGS_PROCESSED = "settings_processed"
        private val FRAGMENT_TAG = "current_fragment"
    }


}