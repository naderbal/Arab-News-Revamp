package com.knowledgeview.tablet.arabnews.view.fragments

import android.content.ClipData
import android.content.ClipDescription
import android.os.Bundle
import android.text.TextUtils
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.models.data.ReadingList
import com.knowledgeview.tablet.arabnews.models.data.SectionListing
import com.knowledgeview.tablet.arabnews.models.data.SectionListingResult
import com.knowledgeview.tablet.arabnews.models.local.DaoAccess
import com.knowledgeview.tablet.arabnews.models.networking.apis.GetNews
import com.knowledgeview.tablet.arabnews.utils.EndlessScrollListener
import com.knowledgeview.tablet.arabnews.view.adapters.NewsListingAdapter
import dagger.android.support.DaggerFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ScreenListingFragment : DaggerFragment() {

    @Inject
    lateinit var getNews: GetNews
    @Inject
    lateinit var newsDao: DaoAccess
    private var tid: String? = null
    private var p = 0
    private var progressBar: ProgressBar? = null
    private var total: Int = 0
    private var adapter: NewsListingAdapter? = null
    private var dragPosition: Int = -1
    private var news: MutableList<SectionListing> = mutableListOf()

    lateinit var newsListing: RecyclerView
    var listPositionListener : ListPostionListener? = null
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.screen_liting_fragment, container, false)
        progressBar = view.findViewById(R.id.pb)
        progressBar!!.visibility = View.VISIBLE
        val readingListFloating = view.findViewById<FloatingActionButton>(R.id.reading_listing_floating)
        readingListFloating.setOnDragListener(dragListen)
        val args = arguments
        if (args != null) {
            tid = args.get("tid") as String
            if (!TextUtils.isEmpty(tid)) {
                p = 0
                news = mutableListOf()

                newsListing = view.findViewById<RecyclerView>(R.id.news_listing)
                linearLayoutManager = LinearLayoutManager(context!!, RecyclerView.VERTICAL, false)
                newsListing.layoutManager = linearLayoutManager
                news = newsDao.getAllListing(tid!!.toInt())
                if (!news.isNullOrEmpty()) progressBar!!.visibility = View.GONE
                adapter = NewsListingAdapter(context!!, news)
                newsListing.adapter = adapter
                refreshNews(tid!!)
//                newsListing.addOnScrollListener(object : EndlessScrollListener(linearLayoutManager) {
//                    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
//                        if (news.size < total) {
//                            p++
//                            refreshNews(tid!!)
//                        }
//                    }
//                })
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsListing.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                listPositionListener?.onListPositionChanged(linearLayoutManager.findFirstVisibleItemPosition())
            }
        })
    }

    private val dragListen = View.OnDragListener { v, event ->
        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                v.invalidate()
                true
            }

            DragEvent.ACTION_DRAG_LOCATION ->
                true
            DragEvent.ACTION_DRAG_EXITED -> {

                true
            }
            DragEvent.ACTION_DROP -> {
                val item: ClipData.Item = event.clipData.getItemAt(0)
                dragPosition = item.text.toString().toInt()
                true
            }

            DragEvent.ACTION_DRAG_ENDED -> {
                if (event.result) {
                    if (dragPosition != -1) {
                        val readingList = ReadingList(news[dragPosition])
                        newsDao.insertReadingList(readingList)
                    }
                    Toast.makeText(context, "Item added to reading list successfully", Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(context, "Item not added to reading list", Toast.LENGTH_LONG).show()
                }
                true
            }
            else -> {
                false
            }
        }
    }

    private fun refreshNews(sectionID: String) {
        getNews.getNewsListing(sectionID, p.toString()).enqueue(object : Callback<SectionListingResult> {
            override fun onFailure(call: Call<SectionListingResult>, t: Throwable) {
//                if (news.isNullOrEmpty()) connection!!.visibility = View.VISIBLE
                progressBar!!.visibility = View.GONE
                Toast.makeText(context, "An error has occurred", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<SectionListingResult>, response: Response<SectionListingResult>) {
                val sectionIDInt = sectionID.toInt()
                progressBar!!.visibility = View.GONE
                if (response.body() != null && response.body()!!.data != null
                        && !response.body()!!.data!!.sectionListing.isNullOrEmpty()) {
                    if (p == 0) {
                        newsDao.deleteNews(sectionIDInt)
                    }
                    for (item in response.body()!!.data!!.sectionListing!!) {
                        item.setCategoryID(sectionIDInt)
                    }
                    newsDao.insertNews(response.body()!!.data!!.sectionListing!!)
                    if (p > 0)
                        news.addAll(response.body()!!.data!!.sectionListing!!)
                    else
                        news = response.body()!!.data!!.sectionListing!!
                    total = response.body()!!.total
                    adapter!!.addNews(news)
                }
            }
        })
    }
}