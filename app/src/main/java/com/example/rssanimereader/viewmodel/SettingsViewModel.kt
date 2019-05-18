package com.example.rssanimereader.viewmodel

import androidx.lifecycle.ViewModel
import com.example.rssanimereader.entity.SettingsItem
import com.example.rssanimereader.model.dataSource.SettingsDataSource

class SettingsViewModel(private val settingsDataSource: SettingsDataSource): ViewModel(){

    var settingsItem: SettingsItem = settingsDataSource.getSettings()


    fun nightModeClicked(){
        settingsDataSource.saveNightModeValue(!settingsItem.nightModeValue)
        settingsItem = settingsDataSource.getSettings()
    }


}