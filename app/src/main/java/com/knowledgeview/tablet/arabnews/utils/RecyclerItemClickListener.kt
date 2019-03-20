package com.knowledgeview.tablet.arabnews.utils

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Miriana on 1/26/2018.
 */
open class RecyclerItemClickListener(context: Context, listener: OnItemClickListener) : RecyclerView.OnItemTouchListener {

    private var listener: OnItemClickListener? = null

    private var gestureDetector: GestureDetector? = null

    @Nullable
    private var childView: View? = null

    private var childViewPosition: Int = 0

    init {
        this.gestureDetector = GestureDetector(context, GestureListener())
        this.listener = listener
    }

    override fun onInterceptTouchEvent(view: RecyclerView, event: MotionEvent): Boolean {
        childView = view.findChildViewUnder(event.x, event.y)
        if (childView != null) {
            childViewPosition = view.getChildAdapterPosition(childView!!)
        } else return false
        return childView != null && gestureDetector!!.onTouchEvent(event)
    }

    override fun onTouchEvent(view: RecyclerView, event: MotionEvent) {
        // Not needed.
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }

    /**
     * A click listener for items.
     */
    interface OnItemClickListener {

        /**
         * Called when an item is clicked.
         *
         * @param childView View of the item that was clicked.
         * @param position  Position of the item that was clicked.
         */
        fun onItemClick(childView: View, position: Int)

        /**
         * Called when an item is long pressed.
         *
         * @param childView View of the item that was long pressed.
         * @param position  Position of the item that was long pressed.
         */
        fun onItemLongPress(childView: View, position: Int)

    }

    /**
     * A simple click listener whose methods can be overridden one by one.
     */
    abstract class SimpleOnItemClickListener : OnItemClickListener {

        /**
         * Called when an item is clicked. The default implementation is a no-op.
         *
         * @param childView View of the item that was clicked.
         * @param position  Position of the item that was clicked.
         */
        override fun onItemClick(childView: View, position: Int) {
            // Do nothing.
        }

        /**
         * Called when an item is long pressed. The default implementation is a no-op.
         *
         * @param childView View of the item that was long pressed.
         * @param position  Position of the item that was long pressed.
         */
        override fun onItemLongPress(childView: View, position: Int) {
            // Do nothing.
        }

    }

    protected inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapUp(event: MotionEvent): Boolean {
            if (childView != null) {
                listener!!.onItemClick(childView!!, childViewPosition)
            }

            return true
        }

        override fun onLongPress(event: MotionEvent) {
            if (childView != null) {
                listener!!.onItemLongPress(childView!!, childViewPosition)
            }
        }

        override fun onDown(event: MotionEvent): Boolean {
            // Best practice to always return true here.
            // http://developer.android.com/training/gestures/detector.html#detect
            return true
        }

    }

}

