package com.knowledgeview.tablet.arabnews.view.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.knowledgeview.tablet.arabnews.R

class BulletListAdapter(private val bullets:List<String>,val textColor:Boolean): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
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
        }
    }

    private class ViewItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bulletItem = itemView.findViewById<TextView>(R.id.bullet_text)!!
    }

}