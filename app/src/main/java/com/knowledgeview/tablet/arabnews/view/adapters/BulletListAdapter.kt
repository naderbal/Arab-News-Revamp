package com.knowledgeview.tablet.arabnews.view.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.utils.Methods

class BulletListAdapter(private val bullets:List<String>,val textColor:Boolean): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var sizeDifference = 0

    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val v = LayoutInflater.from(parent.context).inflate(R.layout.bullet_item, parent, false)
        return BulletListAdapter.ViewItem(v)
    }

    override fun getItemCount(): Int {
        return bullets.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemView = holder as BulletListAdapter.ViewItem
        itemView.bulletItem.text= "\u2022  "+bullets[position]
        if(!textColor){
            itemView.bulletItem.setTextColor(Color.BLACK)
            if(sizeDifference != 0) {
                val dateTextSize = Methods.pixelsToSp(context!!, itemView.bulletItem.textSize) + sizeDifference
                itemView.bulletItem.textSize = dateTextSize
            }
        }
    }

    fun updateTextSize(sizeDifference: Int) {
        this.sizeDifference = sizeDifference
        notifyDataSetChanged()
    }

    private class ViewItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bulletItem = itemView.findViewById<TextView>(R.id.bullet_text)!!
    }

}