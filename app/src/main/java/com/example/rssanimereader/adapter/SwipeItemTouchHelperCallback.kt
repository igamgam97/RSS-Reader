package com.example.rssanimereader.adapter

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.rssanimereader.adapter.util.ViewUtil


class SwipeItemTouchHelperCallback private constructor(dragDirs: Int, swipeDirs: Int) :
    ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    lateinit var drawableLeft: Drawable
    lateinit var drawableRight: Drawable
    lateinit var paintLeft: Paint
    lateinit var paintRight: Paint
    lateinit var onItemSwipeLeftListener: OnItemSwipeListener
    lateinit var onItemSwipeRightListener: OnItemSwipeListener
    var swipeEnabled: Boolean = false

    private constructor(builder: Builder) : this(builder.dragDirs, builder.swipeDirs) {
        paintLeft = Paint(Paint.ANTI_ALIAS_FLAG)
        paintRight = Paint(Paint.ANTI_ALIAS_FLAG)
        setPaintColor(paintLeft, builder.bgColorSwipeLeft)
        setPaintColor(paintRight, builder.bgColorSwipeRight)
        drawableLeft = builder.drawableSwipeLeft!!
        drawableRight = builder.drawableSwipeRight!!
        swipeEnabled = builder.swipeEnabled
        onItemSwipeLeftListener = builder.onItemSwipeLeftListener!!
        onItemSwipeRightListener = builder.onItemSwipeRightListener!!
    }

    private fun setPaintColor(paint: Paint, color: Int) {
        paint.setColor(color)
    }


    override fun isItemViewSwipeEnabled(): Boolean {
        return swipeEnabled
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        if (direction == ItemTouchHelper.LEFT) {
            onItemSwipeLeftListener!!.onItemSwiped(position)
        } else if (direction == ItemTouchHelper.RIGHT) {
            onItemSwipeRightListener!!.onItemSwiped(position)
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

            val itemView = viewHolder.itemView
            val height = itemView.bottom.toFloat() - itemView.top .toFloat()
            val width = height / 3

            if (dX > 0) {
                val background = RectF(itemView.left .toFloat(), itemView.top .toFloat(), dX, itemView.bottom .toFloat())
                val iconDest = RectF(
                    itemView.left .toFloat() + width,
                    itemView.top .toFloat() + width,
                    itemView.left .toFloat() + 2 * width,
                    itemView.bottom .toFloat() - width
                )
                c.drawRect(background, paintLeft)
                c.drawBitmap(ViewUtil.getBitmap(drawableLeft!!), null, iconDest, paintLeft)
            } else {
                val background = RectF(
                    itemView.right .toFloat() + dX,
                    itemView.top .toFloat(),
                    itemView.right .toFloat(),
                    itemView.bottom .toFloat()
                )
                val iconDest = RectF(
                    itemView.right .toFloat() - 2 * width,
                    itemView.top .toFloat() + width,
                    itemView.right .toFloat() - width,
                    itemView.bottom .toFloat() - width
                )
                c.drawRect(background, paintRight)
                c.drawBitmap(ViewUtil.getBitmap(drawableRight!!), null, iconDest, paintRight)
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    interface OnItemSwipeListener {
        fun onItemSwiped(position: Int)
    }

    class Builder(val dragDirs: Int, val swipeDirs: Int) {
        var drawableSwipeLeft: Drawable? = null
        var drawableSwipeRight: Drawable? = null
        var bgColorSwipeLeft: Int = 0
        var bgColorSwipeRight: Int = 0
        var onItemSwipeLeftListener: OnItemSwipeListener? = null
        var onItemSwipeRightListener: OnItemSwipeListener? = null
        var swipeEnabled: Boolean = false

        fun drawableSwipeLeft(`val`: Drawable): Builder {
            drawableSwipeLeft = `val`
            return this
        }

        fun drawableSwipeRight(`val`: Drawable): Builder {
            drawableSwipeRight = `val`
            return this
        }

        fun bgColorSwipeLeft(`val`: Int): Builder {
            bgColorSwipeLeft = `val`
            return this
        }

        fun bgColorSwipeRight(`val`: Int): Builder {
            bgColorSwipeRight = `val`
            return this
        }

        fun onItemSwipeLeftListener(`val`: OnItemSwipeListener): Builder {
            onItemSwipeLeftListener = `val`
            return this
        }

        fun onItemSwipeRightListener(`val`: OnItemSwipeListener): Builder {
            onItemSwipeRightListener = `val`
            return this
        }

        fun setSwipeEnabled(`val`: Boolean): Builder {
            swipeEnabled = `val`
            return this
        }

        fun build(): SwipeItemTouchHelperCallback {
            return SwipeItemTouchHelperCallback(this)
        }
    }
}