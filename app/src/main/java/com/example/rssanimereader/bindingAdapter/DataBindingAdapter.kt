package com.example.rssanimereader.bindingAdapter

import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods


@BindingMethods(
    BindingMethod(
        type = BottomNavigationView::class,
        attribute = "app:onNavigationItemSelected",
        method = "setOnNavigationItemSelectedListener"
    )
)
class DataBindingAdapter{

}