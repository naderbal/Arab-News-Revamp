package com.knowledgeview.tablet.arabnews.view.fragments.opinions.listing

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.models.data.Author
import com.knowledgeview.tablet.arabnews.models.data.HomeData
import com.knowledgeview.tablet.arabnews.utils.Methods
import com.knowledgeview.tablet.arabnews.view.adapters.AuthorsListAdapter
import com.knowledgeview.tablet.arabnews.view.adapters.CartoonListAdapter
import com.knowledgeview.tablet.arabnews.view.adapters.OpinionListAdapter
import com.knowledgeview.tablet.arabnews.view.fragments.opinions.OpinionsListListener
import com.knowledgeview.tablet.arabnews.view.ui.CircleTransform
import com.squareup.picasso.Picasso

/**
 * Created by naderbaltaji on 3/22/19
 */
class OpinionsListingAdapter(val listener: OpinionsListListener?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var context : Context? = null
    var items = ArrayList<OpinionsListingItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context

        var holder: RecyclerView.ViewHolder
        when(viewType) {
            OpinionsListingItemType.HEADER.ordinal -> {
                val view = inflater.inflate(R.layout.row_header, parent, false)
                holder = HeaderViewHolder(view)
            }
            OpinionsListingItemType.CARTOON.ordinal -> {
                val view = inflater.inflate(R.layout.layout_list, parent, false)
                holder = CartoonsViewHolder(view)
            }
            OpinionsListingItemType.OPINIONS.ordinal -> {
                val view = inflater.inflate(R.layout.layout_list, parent, false)
                holder = OpinionsViewHolder(view)
            }
            OpinionsListingItemType.AUTHORS.ordinal -> {
                val view = inflater.inflate(R.layout.layout_list, parent, false)
                holder = AuthorsViewHolder(view)
            }
            OpinionsListingItemType.AUTHOR_HEADER.ordinal -> {
                val view = inflater.inflate(R.layout.author_header_item, parent, false)
                holder = AuthorHeaderViewHolder(view)
            }
            else ->{
                val view = inflater.inflate(R.layout.layout_list, parent, false)
                holder = HeaderViewHolder(view)
            }

        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is HeaderViewHolder -> {
                val headerItem = items[position] as OpinionsHeaderListinItem
                holder.bind(headerItem.header)
            }
            is CartoonsViewHolder -> {
                val cartoonItem = items[position] as OpinionsCartoonListingItem
                holder.bind(cartoonItem.data)
            }
            is OpinionsViewHolder -> {
                val opinionItem = items[position] as OpinionsOpinionsListingItem
                holder.bind(opinionItem.data)
            }
            is AuthorsViewHolder -> {
                val authorsItem = items[position] as OpinionsAuthorsListingItem
                holder.bind(authorsItem.authors)
            }
            is AuthorHeaderViewHolder -> {
                val authorsItem = items[position] as OpinionsAuthorHeaderListingItem
                holder.bind(authorsItem.data)
            }

        }
    }

    fun addItems(items: ArrayList<OpinionsListingItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
       return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type.ordinal
    }

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mTvHeader : TextView = view.findViewById(R.id.tv_header)

        fun bind(header: String) {
            mTvHeader.text = header
        }
    }

    inner class CartoonsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mRvContent: RecyclerView = view.findViewById(R.id.rv_content)

        fun bind(readingList: List<HomeData>) {
            mRvContent.layoutManager = LinearLayoutManager(context)
            ViewCompat.setNestedScrollingEnabled(mRvContent, false)
            val adapter = CartoonListAdapter(context!!, readingList, 1)
            mRvContent.adapter = adapter
        }
    }

    inner class OpinionsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mRvContent: RecyclerView = view.findViewById(R.id.rv_content)

        fun bind(readingList: List<HomeData>) {
            mRvContent.layoutManager = LinearLayoutManager(context)
            ViewCompat.setNestedScrollingEnabled(mRvContent, false)
            val adapter = OpinionListAdapter(context!!, readingList, 1, false)
            mRvContent.adapter = adapter
            mRvContent.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    inner class AuthorsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mRvContent: RecyclerView = view.findViewById(R.id.rv_content)

        fun bind(authors: List<Author>) {
            mRvContent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            ViewCompat.setNestedScrollingEnabled(mRvContent, false)
            val adapter = AuthorsListAdapter(authors, object : OpinionsListListener{
                override fun onAuthorClicked(author: Author) {
                    listener?.onAuthorClicked(author)
                }

            })
            mRvContent.adapter = adapter
        }
    }

    inner class AuthorHeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mTvAuthorName: TextView = view.findViewById(R.id.tv_author_name)
        var mWvDetails: WebView = view.findViewById(R.id.wv_content)
        var mIvAuthor: ImageView = view.findViewById(R.id.iv_author_image)


        fun bind(author: HomeData) {
            val authorDetails = author.getAuthorDetails()
            mTvAuthorName.text = authorDetails?.name
            Picasso.get().load(authorDetails?.authorImage)
                    .transform(CircleTransform())
                    .fit()
                    .centerCrop()
                    .into(mIvAuthor)

            mWvDetails.settings.javaScriptEnabled = true
            mWvDetails.setBackgroundColor(Color.argb(1, 0, 0, 0))// for flickering
            mWvDetails.loadDataWithBaseURL("http://www.arabnews.com/", Methods.formatTextWebView(context!!,
                    loadHtml(authorDetails?.description!!)),
                    "text/html", "utf-8", null)
        }
    }

    fun loadHtml(html: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html);
        }.toString()
    }

}