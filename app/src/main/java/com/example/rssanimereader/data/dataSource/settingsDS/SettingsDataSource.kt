package com.example.rssanimereader.data.dataSource.settingsDS

import android.content.SharedPreferences
import com.example.rssanimereader.domain.entity.SettingsItem

class SettingsDataSource(private val sharedPreferences: SharedPreferences) {


    fun getSettings() = SettingsItem(
        sharedPreferences.getBoolean(NIGHT_MODE_VALUE, false)
    )

    fun saveNightModeValue(value: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(NIGHT_MODE_VALUE, value)
        }.apply()
    }

    private companion object {
        const val NIGHT_MODE_VALUE = "NIGHT_MODE_VALUE"
    }
}