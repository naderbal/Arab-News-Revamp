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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.models.data.SectionListing
import com.knowledgeview.tablet.arabnews.utils.Methods
import com.knowledgeview.tablet.arabnews.view.NodeDetailsActivity
import com.knowledgeview.tablet.arabnews.view.ui.CustomShadowBuilder
import com.squareup.picasso.Picasso

class NewsListingAdapter(private var context: Context, private var news: List<SectionListing>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val header: Int = 0
    private val item: Int = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            header -> {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.face_item_news, parent, false)
                ViewHeader(v)
            }
            else -> {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
                ViewItem(v)
            }
        }
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position < news.size) {
            val section = news[position]
            when (holder.itemViewType) {
                0 -> {
                    val headerView = holder as NewsListingAdapter.ViewHeader
                    if (!section.getPictureLarge().isNullOrEmpty()) {
                        Picasso.get().load(section.getPictureLarge()!![0]).fit().centerCrop().into(headerView.newsImage)
                    }
                    headerView.bullets.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL
                            , false)
                    if (!section.getBullets().isNullOrEmpty())
                        headerView.bullets.adapter = BulletListAdapter(section.getBullets()!!, true)
                    headerView.newsImage.tag = "$position"
                    headerView.newsImage.setOnLongClickListener { v: View ->
                        val item = ClipData.Item(v.tag as? CharSequence)
                        val dragData = ClipData(
                                v.tag as? CharSequence,
                                arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                                item)
                        val myShadow = CustomShadowBuilder(headerView.newsImage)
                        v.startDrag(
                                dragData,   // the data to be dragged
                                myShadow,   // the drag shadow builder
                                null,       // no need to use local data
                                0           // flags (not currently used, set to 0)
                        )
                    }
                    if (!section.getLabel().isNullOrEmpty()) headerView.newsHeadline.text = section.getLabel()
                    if (!section.getAuthor().isNullOrEmpty()) headerView.author.text = section.getAuthor()!![0]
                    if (section.getDate() != null)
                        headerView.date.text = Methods.dateFormatterString(section.getDate()!!)

                    headerView.newsImage.setOnClickListener {
                        val intent = Intent(context, NodeDetailsActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        intent.putExtra("entityID", section.getEntityID())
                        context.startActivity(intent)
                    }
                }
                1 -> {
                    val itemView = holder as NewsListingAdapter.ViewItem
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
                        true
                    }
                    if (!section.getLabel().isNullOrEmpty()) itemView.newsHeadline.text = section.getLabel()
                    if (!section.getAuthor().isNullOrEmpty()) itemView.author.text = section.getAuthor()!![0]
                    if (section.getDate() != null)
                        itemView.date.text = Methods.dateFormatterString(section.getDate()!!)
                    itemView.news.setOnClickListener {
                        val intent = Intent(context, NodeDetailsActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        intent.putExtra("entityID", section.getEntityID())
                        context.startActivity(intent)
                    }
                    itemView.newsImage.setOnClickListener {
                        val intent = Intent(context, NodeDetailsActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        intent.putExtra("entityID", section.getEntityID())
                        context.startActivity(intent)
                    }
                }
            }
        }
    }

    fun addNews(news: List<SectionListing>) {
        this.news = news
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 && news[position].getFace()!!)
            header
        else item
    }

    private class ViewItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val news = itemView.findViewById<RelativeLayout>(R.id.news)
        val newsImage = itemView.findViewById<ImageView>(R.id.item_news_image)!!
        val newsHeadline = itemView.findViewById<TextView>(R.id.item_news_headline)!!
        val author = itemView.findViewById<TextView>(R.id.author)!!
        val date = itemView.findViewById<TextView>(R.id.date)!!
    }

    private class ViewHeader(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val newsImage = itemView.findViewById<ImageView>(R.id.face_news_image)!!
        val newsHeadline = itemView.findViewById<TextView>(R.id.item_label)!!
        val author = itemView.findViewById<TextView>(R.id.author)!!
        val date = itemView.findViewById<TextView>(R.id.date)!!
        val bullets = itemView.findViewById<RecyclerView>(R.id.bullets)!!
    }
}