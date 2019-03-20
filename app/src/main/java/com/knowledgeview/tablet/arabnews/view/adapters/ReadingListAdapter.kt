package com.knowledgeview.tablet.arabnews.view.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.models.data.ReadingList
import com.knowledgeview.tablet.arabnews.utils.Methods
import com.knowledgeview.tablet.arabnews.view.NodeDetailsActivity
import com.knowledgeview.tablet.arabnews.view.ui.CircleTransform
import com.squareup.picasso.Picasso

class ReadingListAdapter(private val context:Context,private val readingList: List<ReadingList>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//    private val header: Int = 0
//    private val item: Int = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.reading_list_item, parent, false)
        return ReadingListAdapter.ViewItem(v)
    }

    override fun getItemCount(): Int {
        return readingList.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val readingItem = readingList[position]
        val itemView = holder as ReadingListAdapter.ViewItem
        if (!readingItem.getImages().isNullOrEmpty()) {
            if (readingItem.getType() != "opinion") {
                Picasso.get().load(readingItem.getImages()!![0]).fit().centerCrop().into(itemView.newsImage)
            } else Picasso.get().load(readingItem.getImages()!![0])
                    .transform(CircleTransform()).into(itemView.newsImage)
        }
        if (!readingItem.getAuthor().isNullOrEmpty()) itemView.author.text = readingItem.getAuthor()
        if (!readingItem.getLabel().isNullOrEmpty()) itemView.newsHeadline.text = readingItem.getLabel()
        if (readingItem.getDate() != null)
            itemView.date.text = Methods.dateFormatterString(readingItem.getDate()!!)
        itemView.reading.setOnClickListener {
            val intent = Intent(context, NodeDetailsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("entityID",readingList[position].getEntityID())
            context.startActivity(intent)
        }
    }

    private class ViewItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reading = itemView.findViewById<LinearLayout>(R.id.reading_list)
        val newsImage = itemView.findViewById<ImageView>(R.id.reading_list_item_image)!!
        val newsHeadline = itemView.findViewById<TextView>(R.id.reading_list_item_label)!!
        val author = itemView.findViewById<TextView>(R.id.author)!!
        val date = itemView.findViewById<TextView>(R.id.date)!!
    }

}