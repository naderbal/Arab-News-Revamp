package com.knowledgeview.tablet.arabnews.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.models.data.HomeData
import com.knowledgeview.tablet.arabnews.models.data.SectionListing
import com.squareup.picasso.Picasso

class FaceListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val list = ArrayList<SectionListing>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return FaceListAdapter.ViewHeader(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemView = holder as FaceListAdapter.ViewHeader
        val item = list[position]
        if(!item.getPictureSmall().isNullOrEmpty()) {
            if (!item.getPictureSmall()!![0].isEmpty()){
                Picasso.get().load(item.getPictureSmall()!![0])
                        .fit()
                        .centerCrop()
                        .into(itemView.ivImage)
            }
        }
    }

    fun addImages(list: List<SectionListing>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    private class ViewHeader(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImage = itemView.findViewById<ImageView>(R.id.iv_image)!!
    }
}