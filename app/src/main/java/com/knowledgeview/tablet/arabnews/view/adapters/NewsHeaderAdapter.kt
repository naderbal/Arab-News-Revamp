package com.knowledgeview.tablet.arabnews.view.adapters

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.models.data.HomeData
import com.knowledgeview.tablet.arabnews.models.data.ReadingList
import com.knowledgeview.tablet.arabnews.utils.Methods
import com.knowledgeview.tablet.arabnews.view.NodeDetailsActivity
import com.knowledgeview.tablet.arabnews.view.ui.CustomShadowBuilder
import com.squareup.picasso.Picasso

class NewsHeaderAdapter(private val context: Context, private val readingList: List<HomeData>,
                        private val groupPosition:Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val header: Int = 0
    private val item: Int = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            header -> {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.reading_list_item_header, parent, false)
                NewsHeaderAdapter.ViewHeader(v)
            }
            else -> {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.reading_list_item, parent, false)
                return NewsHeaderAdapter.ViewItem(v)
            }
        }
    }

    override fun getItemCount(): Int {
        return readingList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> header
            else -> item
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val readingItem = readingList[position]
        when (holder.itemViewType) {
            0 -> {
                val itemView = holder as NewsHeaderAdapter.ViewHeader
                if (!readingItem.getPictureSmall().isNullOrEmpty()) {
                    Picasso.get().load(readingItem.getPictureSmall()!![0]).fit().centerCrop().into(itemView.newsImage)
                }
                if (!readingItem.getLabel().isNullOrEmpty()) itemView.newsHeadline.text = readingItem.getLabel()
                if (!readingItem.getAuthor().isNullOrEmpty()){
                    itemView.author.visibility = View.VISIBLE
                    itemView.author.text = readingItem.getAuthor()!![0]
                } else{
                    itemView.author.visibility = View.GONE
                }
                if (readingItem.getDate() != null)
                    itemView.date.text = Methods.dateFormatterString(readingItem.getDate()!!)
                itemView.newsHeadline.tag = "$groupPosition - $position"
                itemView.newsHeadline.setOnLongClickListener { v: View ->
                    val item = ClipData.Item(v.tag as? CharSequence)
                    val dragData = ClipData(
                            v.tag as? CharSequence,
                            arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                            item)
                    val myShadow = CustomShadowBuilder(itemView.newsHeadline)
                    v.startDrag(
                            dragData,   // the data to be dragged
                            myShadow,   // the drag shadow builder
                            null,       // no need to use local data
                            0           // flags (not currently used, set to 0)
                    )
                }
                itemView.newsHeadline.setOnClickListener {
                    openNodeActivity(position)
                }
                itemView.newsImage.setOnClickListener {
                    openNodeActivity(position)
                }
            }
            1 -> {
                val itemView = holder as NewsHeaderAdapter.ViewItem
                if (!readingItem.getPictureSmall().isNullOrEmpty()) {
                    Picasso.get().load(readingItem.getPictureSmall()!![0]).fit().centerCrop().into(itemView.newsImage)
                }
                if (!readingItem.getAuthor().isNullOrEmpty()){
                    itemView.author.visibility = View.VISIBLE
                    itemView.author.text = readingItem.getAuthor()!![0]
                } else {
                    itemView.author.visibility = View.GONE
                }
                if (!readingItem.getLabel().isNullOrEmpty()) itemView.newsHeadline.text = readingItem.getLabel()
                if (readingItem.getDate() != null)
                    itemView.date.text = Methods.dateFormatterString(readingItem.getDate()!!)
                itemView.newsHeadline.tag = "$groupPosition - $position"
                itemView.newsHeadline.setOnLongClickListener { v: View ->
                    val item = ClipData.Item(v.tag as? CharSequence)
                    val dragData = ClipData(
                            v.tag as? CharSequence,
                            arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                            item)
                    val myShadow = CustomShadowBuilder(itemView.newsHeadline)
                    v.startDrag(
                            dragData,   // the data to be dragged
                            myShadow,   // the drag shadow builder
                            null,       // no need to use local data
                            0           // flags (not currently used, set to 0)
                    )
                }
                itemView.newsHeadline.setOnClickListener {
                    openNodeActivity(position)
                }
                itemView.newsImage.setOnClickListener {
                    openNodeActivity(position)
                }
            }
        }
    }

    private fun openNodeActivity(position: Int) {
        val intent = Intent(context, NodeDetailsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("entityID", readingList[position].getEntityID())
        context.startActivity(intent)
    }

    private class ViewItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val news= itemView.findViewById<LinearLayout>(R.id.reading_list)
        val newsImage = itemView.findViewById<ImageView>(R.id.reading_list_item_image)!!
        val newsHeadline = itemView.findViewById<TextView>(R.id.reading_list_item_label)!!
        val author = itemView.findViewById<TextView>(R.id.author)!!
        val date = itemView.findViewById<TextView>(R.id.date)!!
    }

    private class ViewHeader(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val news = itemView.findViewById<RelativeLayout>(R.id.news)
        val newsImage = itemView.findViewById<ImageView>(R.id.reading_list_item_image)!!
        val newsHeadline = itemView.findViewById<TextView>(R.id.reading_list_item_label)!!
        val author = itemView.findViewById<TextView>(R.id.author)!!
        val date = itemView.findViewById<TextView>(R.id.date)!!
    }

}