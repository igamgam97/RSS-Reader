package com.example.rssanimereader.bindingAdapter

import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import androidx.lifecycle.MutableLiveData
import com.example.rssanimereader.R
import com.example.rssanimereader.view.ListOfTypeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


@BindingMethods(
        BindingMethod(
                type = BottomNavigationView::class,
                attribute = "app:onNavigationItemSelected",
                method = "setOnNavigationItemSelectedListener"
        ),
    BindingMethod(
        type = BottomNavigationView::class,
        attribute = "app:onSelectedItemId",
        method = "setSelectedItemId"
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
    method = "setSpinnerItemSelectedListener"
    )
)
class SpinnerViewDataBindingAdapter{}


object SimpleBindingAdapter {
    @BindingAdapter("android:loadImage")
    @JvmStatic
    fun setImageViewResource(imageView: ImageView, path:String) {
        imageView.setImageURI(Uri.parse(path))
    }



    @BindingAdapter("binding:onSelectecItemId")
    @JvmStatic
    fun selectItemId(bottomNavigationView: BottomNavigationView,tagFragment: MutableLiveData<ListOfTypeFragment>) {
        Log.d("bag","there")
        tagFragment.value?.let {
            val itemID = when (it) {
                ListOfTypeFragment.ChannelListFragment -> R.id.app_bar_channels
                ListOfTypeFragment.FeedListFragment -> R.id.app_bar_feeds
                else ->  R.id.app_bar_settings
            }
            bottomNavigationView.menu.findItem(itemID).isChecked = true
        }

    }



}