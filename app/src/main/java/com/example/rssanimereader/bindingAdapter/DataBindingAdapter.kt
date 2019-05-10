package com.example.rssanimereader.bindingAdapter

import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
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

object SimpleBindingAdapter{
    @BindingAdapter("android:loadImage")
    @JvmStatic
    fun setImageViewResource(imageView: ImageView, bmp:Bitmap) {
        imageView.setImageBitmap(bmp)
    }
}