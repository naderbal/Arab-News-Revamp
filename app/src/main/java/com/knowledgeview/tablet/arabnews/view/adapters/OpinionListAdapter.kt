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
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.models.data.HomeData
import com.knowledgeview.tablet.arabnews.utils.Methods
import com.knowledgeview.tablet.arabnews.view.OpinionDetailsPageActivity
import com.knowledgeview.tablet.arabnews.view.ui.CircleTransform
import com.knowledgeview.tablet.arabnews.view.ui.CustomShadowBuilder
import com.squareup.picasso.Picasso

class OpinionListAdapter(private val context: Context,private val opinions: List<HomeData>,
                         private val groupPosition:Int,private val isDrag:Boolean) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.opinion_item, parent, false)
        return OpinionListAdapter.ViewHeader(v)
    }

    override fun getItemCount(): Int {
        return opinions.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemView = holder as OpinionListAdapter.ViewHeader
        val opinion = opinions[position]
        if (!opinion.getPictureLarge().isNullOrEmpty())
            Picasso.get().load(opinion.getPictureLarge()!![0])
                    .transform(CircleTransform()).fit().centerCrop().into(itemView.authorImage)
        if (!opinion.getAuthor().isNullOrEmpty())
            itemView.author.text = opinion.getAuthor()!![0]
        if (!opinion.getLabel().isNullOrBlank())
            itemView.label.text = opinion.getLabel()
        if (opinion.getDate() != null) {
            val formattedDate = Methods.dateFormatterString(opinion.getDate()!!)
            itemView.date.text = formattedDate
        }
        if(isDrag) {
            itemView.authorBackground.tag = "$groupPosition - $position"
            itemView.authorBackground.setOnLongClickListener { v: View ->
                val item = ClipData.Item(v.tag as? CharSequence)
                val dragData = ClipData(
                        v.tag as? CharSequence,
                        arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                        item)
                val myShadow = CustomShadowBuilder(itemView.authorBackground)
                v.startDrag(
                        dragData,   // the data to be dragged
                        myShadow,   // the drag shadow builder
                        null,       // no need to use local data
                        0           // flags (not currently used, set to 0)
                )
            }
        }
        itemView.authorBackground.setOnClickListener {
            val intent = Intent(context, OpinionDetailsPageActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            //add author id
            intent.putExtra("entityID",opinion.getEntityID())
            intent.putExtra("authorID",opinion.getAuthorDetails()?.tid)
            context.startActivity(intent)
        }
    }

    private class ViewHeader(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val authorImage = itemView.findViewById<ImageView>(R.id.author_image)!!
        val authorBackground = itemView.findViewById<LinearLayout>(R.id.author_background)!!
        val author = itemView.findViewById<TextView>(R.id.author)!!
        val label = itemView.findViewById<TextView>(R.id.label)!!
        val date = itemView.findViewById<TextView>(R.id.date)!!
    }
}