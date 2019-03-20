package com.knowledgeview.tablet.arabnews.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.models.data.ParentSection

class ExpandableListMenuAdapter(context: Context, parentSections: List<ParentSection>?) : BaseExpandableListAdapter() {
    private var context: Context? = null
    private var parentSections: List<ParentSection>? = null
    var positionClicked: Int = 0

    init {
        this.context = context
        this.parentSections = parentSections
    }

    override fun getGroupCount(): Int {
        return this.parentSections!!.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return if (!this.parentSections!![groupPosition].getChildren().isNullOrEmpty())
            this.parentSections!![groupPosition].getChildren()!!.size
        else 0
    }

    fun getPosition(position: Int) {
        this.positionClicked = position
        notifyDataSetChanged()
    }

    override fun getGroup(groupPosition: Int): Any {
        return this.parentSections!![groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return this.parentSections!![groupPosition].getChildren()!![childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val parentSection = getGroup(groupPosition) as ParentSection
        if (convertView == null) {
            val infalInflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = infalInflater.inflate(R.layout.nav_item, convertView)
        }
        val menuTitle = view!!.findViewById<TextView>(R.id.menu_title)
        val background = view.findViewById<RelativeLayout>(R.id.background)
        if (positionClicked == groupPosition) {
            background.setBackgroundColor(ContextCompat.getColor(context!!, R.color.light_grey))
            menuTitle.setTextColor(ContextCompat.getColor(context!!, R.color.orange))
        } else {
            menuTitle.setTextColor(ContextCompat.getColor(context!!, R.color.black))
            background.setBackgroundColor(ContextCompat.getColor(context!!,R.color.white))
        }
        val arrow = view.findViewById<ImageView>(R.id.arrow)
        menuTitle.text = parentSection.getTitle()
        if (!parentSection.getChildren().isNullOrEmpty()) {
            arrow.visibility = View.VISIBLE
            if (isExpanded) arrow.setImageResource(R.drawable.arrow_collapsed)
            else arrow.setImageResource(R.drawable.arrow_expanded)
        } else arrow.visibility = View.GONE
        return view
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val childSection = getChild(groupPosition, childPosition) as ParentSection
        if (convertView == null) {
            val infalInflater = this.context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = infalInflater.inflate(R.layout.nav_child_item, convertView)
        }
        val menuTitle = view!!.findViewById<TextView>(R.id.menu_child_title)
        menuTitle.text = childSection.getTitle()
        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

}