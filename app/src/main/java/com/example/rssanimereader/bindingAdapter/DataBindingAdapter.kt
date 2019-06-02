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
import com.example.rssanimereader.presentation.view.ListOfTypeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.recyclerview.widget.ItemTouchHelper
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.rssanimereader.adapter.SwipeItemTouchHelperCallback


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

    @BindingAdapter("android:onEnabled")
    @JvmStatic
    fun setEnabled(swipeRefreshLayout:SwipeRefreshLayout, typeOfFeedsList:String) {
        swipeRefreshLayout.isEnabled = typeOfFeedsList!="favorite"

    }

    @BindingAdapter(
        value = [ "swipeEnabled", "drawableSwipeLeft", "drawableSwipeRight", "bgColorSwipeLeft", "bgColorSwipeRight", "onItemSwipeLeft", "onItemSwipeRight" ],
        requireAll = false
    )
    @JvmStatic
    fun setItemSwipeToRecyclerView(
        recyclerView: RecyclerView,
        swipeEnabled: Boolean,
        drawableSwipeLeft: Drawable,
        drawableSwipeRight: Drawable,
        bgColorSwipeLeft: Int,
        bgColorSwipeRight: Int,
        onItemSwipeLeft: SwipeItemTouchHelperCallback.OnItemSwipeListener,
        onItemSwipeRight: SwipeItemTouchHelperCallback.OnItemSwipeListener
    ) {

        val swipeCallback = SwipeItemTouchHelperCallback.Builder(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
            .bgColorSwipeLeft(bgColorSwipeLeft)
            .bgColorSwipeRight(bgColorSwipeRight)
            .drawableSwipeLeft(drawableSwipeLeft)
            .drawableSwipeRight(drawableSwipeRight)
            .setSwipeEnabled(swipeEnabled)
            .onItemSwipeLeftListener(onItemSwipeLeft)
            .onItemSwipeRightListener(onItemSwipeRight)
            .build()

        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }



}

object BindingAdapter {

    /**
     * Bind ItemTouchHelper.SimpleCallback with RecyclerView
     *
     * @param recyclerView        RecyclerView to bind to SwipeItemTouchHelperCallback
     * @param swipeEnabled        enable/disable swipe
     * @param drawableSwipeLeft     drawable shown when swiped left
     * @param drawableSwipeRight    drawable shown when swiped right
     * @param bgColorSwipeLeft    background color when swiped left
     * @param bgColorSwipeRight    background color when swiped right
     * @param onItemSwipeLeft    OnItemSwipeListener for swiped left
     * @param onItemSwipeRight    OnItemSwipeListener for swiped right
     */

}