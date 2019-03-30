package com.knowledgeview.tablet.arabnews.view.adapters

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.models.data.HomeData
import com.knowledgeview.tablet.arabnews.utils.Methods
import com.knowledgeview.tablet.arabnews.view.NodeDetailsActivity
import com.knowledgeview.tablet.arabnews.view.ui.CustomShadowBuilder
import com.squareup.picasso.Picasso

class HomeHeaderAdapter(private val context: Context, private var news: List<HomeData>, private val groupPosition: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return ViewItem(v)
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position < news.size) {
            val section = news[position]
            val itemView = holder as HomeHeaderAdapter.ViewItem
            if (!section.getPictureLarge().isNullOrEmpty()) {
                Picasso.get().load(section.getPictureLarge()!![0]).fit().centerCrop().into(itemView.newsImage)
            }
            itemView.newsImage.tag = "$groupPosition - $position"
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
            if (!section.getAuthor().isNullOrEmpty()) {
                itemView.author.visibility = View.VISIBLE
                itemView.author.text = section.getAuthor()!![0]
            } else {
                itemView.author.visibility = View.GONE
            }
            if (section.getDate() != null) itemView.date.text = Methods.dateFormatterString(section.getDate()!!)
            itemView.newsHeadline.setOnClickListener {
                openNodeDetails(section)
            }
            itemView.newsImage.setOnClickListener {
                openNodeDetails(section)
            }
        }
    }

    private fun openNodeDetails(section: HomeData) {
        val intent = Intent(context, NodeDetailsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("entityID", section.getEntityID())
        context.startActivity(intent)
    }

    private class ViewItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val news = itemView.findViewById<RelativeLayout>(R.id.news)!!
        val newsImage = itemView.findViewById<ImageView>(R.id.item_news_image)!!
        val newsHeadline = itemView.findViewById<TextView>(R.id.item_news_headline)!!
        val author = itemView.findViewById<TextView>(R.id.author)!!
        val date = itemView.findViewById<TextView>(R.id.date)!!
    }
}