package com.knowledgeview.tablet.arabnews.view.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.models.data.HomeData
import com.knowledgeview.tablet.arabnews.view.NodeDetailsActivity
import com.squareup.picasso.Picasso

class PhotosListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val list = ArrayList<HomeData>()
    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val v = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return PhotosListAdapter.ViewHeader(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemView = holder as PhotosListAdapter.ViewHeader
        val item = list[position]
        if (!item.getPictureLarge()!![0].isEmpty()){
            Picasso.get().load(item.getPictureLarge()!![0])
                    .fit()
                    .centerCrop()
                    .into(itemView.ivImage)
        }

        itemView.ivImage.setOnClickListener {
            openNodeDetails(item.getEntityID())
        }

    }

    private fun openNodeDetails(entityId: String) {
        val intent = Intent(context, NodeDetailsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("entityID", entityId)
        context?.startActivity(intent)
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