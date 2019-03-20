package com.knowledgeview.tablet.arabnews.view.adapters

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.models.data.ParentSection
import com.knowledgeview.tablet.arabnews.models.data.Term
import com.knowledgeview.tablet.arabnews.utils.Methods
import com.knowledgeview.tablet.arabnews.view.NodeDetailsActivity
import com.knowledgeview.tablet.arabnews.view.ui.CustomShadowBuilder
import com.squareup.picasso.Picasso

class HomeListingAdapter(private val context: Context, private var news: List<Term>
                         , private val menus: List<ParentSection>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val header: Int = 0
    private val item: Int = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            header -> {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.face_item_news, parent, false)
                ViewHeaderFace(v)
            }
            else -> {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.home_item, parent, false)
                ViewHeader(v)
            }
        }
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 && news[position].value == "main" &&
                news[position].data!![0].getFace()!!)
            header
        else item
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val section = news[position]
        when (holder.itemViewType) {
            0 -> {
                val innerSection = section.data!![0]
                val headerView = holder as HomeListingAdapter.ViewHeaderFace
                if (!innerSection.getPictureLarge().isNullOrEmpty()) {
                    Picasso.get().load(innerSection.getPictureLarge()!![0]).fit().centerCrop().into(headerView.newsImage)
                }
                headerView.bullets.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL
                        , false)
                if (!innerSection.getBullets().isNullOrEmpty())
                    headerView.bullets.adapter = BulletListAdapter(innerSection.getBullets()!!,true)
                headerView.newsImage.tag = "0 - $position"
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
                headerView.newsImage.setOnClickListener {
                    val intent = Intent(context, NodeDetailsActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.putExtra("entityID",innerSection.getEntityID())
                    context.startActivity(intent)
                }
                if (!innerSection.getLabel().isNullOrEmpty()) headerView.newsHeadline.text = innerSection.getLabel()
                if (!innerSection.getAuthor().isNullOrEmpty()) headerView.author.text = innerSection.getAuthor()!![0]
                if (innerSection.getDate() != null)
                    headerView.date.text = Methods.dateFormatterString(innerSection.getDate()!!)
            }
            1 -> {
                val itemView = holder as ViewHeader
                itemView.data.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                if (section.value == "top4" || section.value == "main") {
                    itemView.sectionTitle.visibility = View.GONE
                    itemView.data.adapter = HomeHeaderAdapter(context,section.data!!,position)
                } else if (section.value != "opinion" && section.value != "cartoon") {
                    itemView.sectionTitle.visibility = View.VISIBLE
                    for (menu in menus) {
                        if (menu.getTid() == section.value) {
                            itemView.sectionTitle.text = menu.getTitle()
                        }
                    }
                    itemView.data.adapter = NewsHeaderAdapter(context,section.data!!,position)
                } else if (section.value == "opinion") {
                    itemView.sectionTitle.text = "Opinion"
                    itemView.sectionTitle.visibility = View.VISIBLE
                    itemView.data.adapter = OpinionListAdapter(context,section.data!!,position)
                } else {
                    itemView.sectionTitle.text = "Cartoon"
                    itemView.sectionTitle.visibility = View.VISIBLE
                    itemView.data.adapter = CartoonListAdapter(context,section.data!!,position)
                }
            }
        }
    }

    private class ViewHeader(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sectionTitle = itemView.findViewById<TextView>(R.id.section_title)!!
        val data = itemView.findViewById<RecyclerView>(R.id.data)!!
    }

    private class ViewHeaderFace(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val newsImage = itemView.findViewById<ImageView>(R.id.face_news_image)!!
        val newsHeadline = itemView.findViewById<TextView>(R.id.item_label)!!
        val author = itemView.findViewById<TextView>(R.id.author)!!
        val date = itemView.findViewById<TextView>(R.id.date)!!
        val bullets = itemView.findViewById<RecyclerView>(R.id.bullets)!!
    }
}