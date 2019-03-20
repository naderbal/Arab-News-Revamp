package com.knowledgeview.tablet.arabnews.view.ui

import android.graphics.Canvas
import android.graphics.Point
import android.view.View


class CustomShadowBuilder(v: View) : View.DragShadowBuilder(v) {

    private var mScaleFactor: Point? = null

    // Defines a callback that sends the drag shadow dimensions and touch point back to the
    // system.
    override fun onProvideShadowMetrics(size: Point, touch: Point) {
        // Defines local variables
        val width: Int = view.width / 2
        val height: Int = view.height / 2

        // Sets the width of the shadow to half the width of the original View

        // Sets the height of the shadow to half the height of the original View

        // Sets the size parameter's width and height values. These get back to the system
        // through the size parameter.
        size.set(width, height)
        // Sets size parameter to member that will be used for scaling shadow image.
        mScaleFactor = size

        // Sets the touch point's position to be in the middle of the drag shadow
        touch.set(width / 2, height / 2)
    }

    override fun onDrawShadow(canvas: Canvas) {

        // Draws the ColorDrawable in the Canvas passed in from the system.
        canvas.scale(mScaleFactor!!.x / view.width.toFloat(), mScaleFactor!!.y / view.height.toFloat())
        view.draw(canvas)
    }

}