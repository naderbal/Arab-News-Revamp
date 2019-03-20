package com.knowledgeview.tablet.arabnews.view.fragments

import android.content.ClipData
import android.content.ClipDescription
import android.os.Bundle
import android.text.TextUtils
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.models.data.ReadingList
import com.knowledgeview.tablet.arabnews.models.data.Video
import com.knowledgeview.tablet.arabnews.models.data.VideoListing
import com.knowledgeview.tablet.arabnews.models.local.DaoAccess
import com.knowledgeview.tablet.arabnews.models.networking.apis.GetNews
import com.knowledgeview.tablet.arabnews.utils.EndlessScrollListener
import com.knowledgeview.tablet.arabnews.view.adapters.NewsListingAdapter
import com.knowledgeview.tablet.arabnews.view.adapters.VideoListingAdapter
import dagger.android.support.DaggerFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class VideosFragment : DaggerFragment() {
    @Inject
    lateinit var getNews: GetNews
    @Inject
    lateinit var newsDao: DaoAccess
    private var tid: String? = null
    private var p = 0
    private var total: Int = 0
    private var adapter: VideoListingAdapter? = null
    private var dragPosition: Int = -1
    private var news: MutableList<Video> = mutableListOf()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.video_listing_fragment, container, false)
        val readingListFloating = view.findViewById<FloatingActionButton>(R.id.reading_listing_floating)
        readingListFloating.setOnDragListener(dragListen)
        val args = arguments
        if (args != null) {
            tid = "videos"
            if (!TextUtils.isEmpty(tid)) {
                p = 0
                news = mutableListOf()
                val newsListing = view.findViewById<RecyclerView>(R.id.news_listing)
                val layoutManager = LinearLayoutManager(context!!, RecyclerView.VERTICAL, false)
                newsListing.layoutManager = layoutManager
                news = newsDao.allVideos
                adapter = VideoListingAdapter(context!!,news)
                newsListing.adapter = adapter
                refreshNews(tid!!)
                newsListing.addOnScrollListener(object : EndlessScrollListener(layoutManager) {
                    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                        if (news.size < total) {
                            p++
                            refreshNews(tid!!)
                        }
                    }
                })
            }
        }
        return view
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
        getNews.getVideos("2", p.toString()).enqueue(object : Callback<VideoListing> {
            override fun onFailure(call: Call<VideoListing>, t: Throwable) {
//                if (news.isNullOrEmpty()) connection!!.visibility = View.VISIBLE
            }

            override fun onResponse(call: Call<VideoListing>, response: Response<VideoListing>) {
//                val sectionIDInt = sectionID.toInt()
                if (response.body() != null && response.body()!!.data != null
                        && !response.body()!!.data!!.isNullOrEmpty()) {
                    if (p == 0) {
                        newsDao.deleteVideos()
                    }
                    newsDao.insertVideos(response.body()!!.data!!)
                    if (p > 0)
                        news.addAll(response.body()!!.data!!)
                    else
                        news = response.body()!!.data!!
//                    total = response.body()!!.total
//                    adapter!!.addNews(news)
                }
            }
        })
    }
}