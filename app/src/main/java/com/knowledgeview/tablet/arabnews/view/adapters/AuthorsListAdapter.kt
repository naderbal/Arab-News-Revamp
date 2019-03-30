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
import com.knowledgeview.tablet.arabnews.view.fragments.opinions.OpinionsListListener
import com.knowledgeview.tablet.arabnews.view.ui.CircleTransform
import com.squareup.picasso.Picasso

class AuthorsListAdapter(private val authors: List<Author>, val listener: OpinionsListListener?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.author_item, parent, false)
        return AuthorsListAdapter.ViewHeader(v)
    }

    override fun getItemCount(): Int {
        return authors.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemView = holder as AuthorsListAdapter.ViewHeader
        val author = authors[position]
        if (!author.authorImage.isNullOrEmpty()){
            Picasso.get().load(author.authorImage)
                    .transform(CircleTransform())
                    .fit()
                    .centerCrop()
                    .into(itemView.authorImage)
        }

        if (!author.name.isNullOrEmpty())
            itemView.author.text = author.name

        itemView.authorBackground.setOnClickListener {
            listener?.onAuthorClicked(author)
        }
    }

    private class ViewHeader(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val authorImage = itemView.findViewById<ImageView>(R.id.iv_author_image)!!
        val authorBackground = itemView.findViewById<LinearLayout>(R.id.author_background)!!
        val author = itemView.findViewById<TextView>(R.id.tv_author_name)!!
    }
}