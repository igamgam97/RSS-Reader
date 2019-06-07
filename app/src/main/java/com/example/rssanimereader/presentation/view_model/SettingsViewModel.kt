package com.example.rssanimereader.presentation.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.domain.entity.SettingsItem
import com.example.rssanimereader.model.dataSource.settingsDS.SettingsDataSource

class SettingsViewModel(private val settingsDataSource: SettingsDataSource): ViewModel(){

    val settingsNightMode = MutableLiveData<Boolean>()

    var settingsItem: SettingsItem = settingsDataSource.getSettings()


    fun nightModeClicked(){
        settingsDataSource.saveNightModeValue(!settingsItem.nightModeValue)
        settingsItem = settingsDataSource.getSettings()
        settingsNightMode.value = !settingsItem.nightModeValue
    }


}