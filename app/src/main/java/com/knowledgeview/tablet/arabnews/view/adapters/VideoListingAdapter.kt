package com.knowledgeview.tablet.arabnews.view.adapters

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.models.data.HomeData
import com.knowledgeview.tablet.arabnews.utils.Methods
import com.knowledgeview.tablet.arabnews.view.ui.CustomShadowBuilder
import com.squareup.picasso.Picasso

class VideoListingAdapter(private var context: Context, private var videos:List<HomeData>, val listener: VideoGalleryListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return VideoListingAdapter.ViewItem(v)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemView = holder as VideoListingAdapter.ViewItem
        val section = videos[position]
        if (!section.getPictureLarge().isNullOrEmpty()) {
            Picasso.get().load(section.getPictureLarge()!![0]).fit().centerCrop().into(itemView.newsImage)
        }
        itemView.newsImage.tag = "$position"
        itemView.newsImage.setOnLongClickListener { v: View ->
            val item = ClipData.Item(v.tag as? CharSequence)
            val dragData = ClipData(
                    v.tag as? CharSequence,
                    arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                    item)
            val myShadow = CustomShadowBuilder(itemView.newsImage)
            v.startDrag(
                    dragData,   // the data to be dragged
                    myShadow,   // the drag shadow builder
                    null,       // no need to use local data
                    0           // flags (not currently used, set to 0)
            )
        }
        if (!section.getLabel().isNullOrEmpty()) itemView.newsHeadline.text = section.getLabel()
        if (!section.getAuthor().isNullOrEmpty()) itemView.author.text = section.getAuthor()!![0]
        if (section.getDate() != null)
            itemView.date.text = Methods.dateFormatterString(section.getDate()!!)

        itemView.newsImage.setOnClickListener {
            listener.onVideoItemClicked(section)
        }
        itemView.ivPlay.setOnClickListener {
            listener.onVideoItemClicked(section)
        }
        itemView.newsHeadline.setOnClickListener {
            listener.onVideoItemClicked(section)
        }
    }

    private class ViewItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPlay = itemView.findViewById<ImageView>(R.id.iv_play)!!
        val newsImage = itemView.findViewById<ImageView>(R.id.item_news_image)!!
        val newsHeadline = itemView.findViewById<TextView>(R.id.item_news_headline)!!
        val author = itemView.findViewById<TextView>(R.id.author)!!
        val date = itemView.findViewById<TextView>(R.id.date)!!
    }

    interface VideoGalleryListener {
        fun onVideoItemClicked(video: HomeData)
    }
}