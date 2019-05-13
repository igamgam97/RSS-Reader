package com.example.rssanimereader.bindingAdapter

import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
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
class DataBindingAdapter {

}

object SimpleBindingAdapter {
    @BindingAdapter("android:loadImage")
    @JvmStatic
    fun setImageViewResource(imageView: ImageView, path:String) {
        imageView.setImageURI(Uri.parse(path))
    }
}