package com.example.rssanimereader.binding_adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.rssanimereader.adapter.util.SwipeItemTouchHelperCallback
import com.google.android.material.bottomnavigation.BottomNavigationView


@BindingMethods(
    BindingMethod(
        type = BottomNavigationView::class,
        attribute = "android:onNavigationItemSelected",
        method = "setOnNavigationItemSelectedListener"
    ),
    BindingMethod(
        type = BottomNavigationView::class,
        attribute = "android:onSelectedItemId",
        method = "setSelectedItemId"
    )
)
class NavigationViewDataBindingAdapter

@BindingMethods(
    BindingMethod(
        type = Toolbar::class,
        attribute = "app:onToolbarItemSelected",
        method = "setOnMenuItemClickListener"
    )
)
class ToolbarViewDataBindingAdapter

object SimpleBindingAdapter {
    @BindingAdapter("android:loadImage")
    @JvmStatic
    fun setImageViewResource(imageView: ImageView, path: String) {
        imageView.setImageURI(Uri.parse(path))
    }


    @BindingAdapter("android:swipeToRefreshEnabled")
    @JvmStatic
    fun setEnabled(swipeRefreshLayout: SwipeRefreshLayout, typeOfFeedsList: String) {
        swipeRefreshLayout.isEnabled = typeOfFeedsList != "favorite"

    }

    @BindingAdapter(
        value = [
            "android:swipeEnabled",
            "android:drawableSwipeLeft",
            "android:drawableSwipeRight",
            "android:bgColorSwipeLeft",
            "android:bgColorSwipeRight",
            "android:onItemSwipeLeft",
            "android:onItemSwipeRight"],
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

    @SuppressLint("SetJavaScriptEnabled")
    @BindingAdapter("android:loadData")
    @JvmStatic
    fun loadDataFromHtml(webView: WebView, html: String) {
        /*webView.settings.loadWithOverviewMode = true*/
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                val code = """javascript:(function() {

        var node = document.createElement('style');

        node.type = 'text/css';
        node.innerHTML = 'body {
            color: c0c0c0;
            background-color: #424242;
        }';

        document.head.appendChild(node);

    })()""".trimIndent()

                webView.loadUrl(code)
            }
        }
        webView.setBackgroundColor(Color.parseColor("#424242"))
        webView.loadData(
            "<style>img{display: inline;height: auto;max-width: 100%;}</style>$html",
            "text/html",
            "UTF-8"
        )
    }


}
