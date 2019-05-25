package com.example.rssanimereader.bindingAdapter

import android.graphics.Bitmap
import android.net.Uri
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.google.android.material.bottomnavigation.BottomNavigationView


@BindingMethods(
        BindingMethod(
                type = BottomNavigationView::class,
                attribute = "app:onNavigationItemSelected",
                method = "setOnNavigationItemSelectedListener"
        )
)
class NavigationViewDataBindingAdapter {

}

@BindingMethods(
    BindingMethod(
        type = Toolbar::class,
        attribute = "app:onToolbarItemSelected",
        method = "setOnMenuItemClickListener"
    )
)
class ToolbarViewDataBindingAdapter{}

@BindingMethods(
    BindingMethod(
    type = Spinner::class,
    attribute = "app:onSpinnerItemSelected",
    method = "selectedItemPosition"
    )
)
class SpinnerViewDataBindingAdapter{}


object SimpleBindingAdapter {
    @BindingAdapter("android:loadImage")
    @JvmStatic
    fun setImageViewResource(imageView: ImageView, path:String) {
        imageView.setImageURI(Uri.parse(path))
    }


}