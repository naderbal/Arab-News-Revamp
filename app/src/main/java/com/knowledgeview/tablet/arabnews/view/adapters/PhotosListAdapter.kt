package com.knowledgeview.tablet.arabnews.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.models.data.Author
import com.knowledgeview.tablet.arabnews.models.data.HomeData
import com.knowledgeview.tablet.arabnews.models.data.Term
import com.knowledgeview.tablet.arabnews.view.ui.CircleTransform
import com.squareup.picasso.Picasso

class PhotosListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val list = ArrayList<HomeData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return PhotosListAdapter.ViewHeader(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemView = holder as PhotosListAdapter.ViewHeader
        val author = list[position]
        if (!author.getPictureLarge()!![0].isEmpty()){
            Picasso.get().load(author.getPictureLarge()!![0])
                    .fit()
                    .centerCrop()
                    .into(itemView.ivImage)
        }
    }

    fun addImages(list: List<HomeData>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    private class ViewHeader(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImage = itemView.findViewById<ImageView>(R.id.iv_image)!!
    }
}