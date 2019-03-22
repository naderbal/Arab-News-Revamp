package com.knowledgeview.tablet.arabnews.view.fragments

import android.content.ClipData
import android.content.ClipDescription
import android.os.Bundle
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.di.ViewModelFactory
import com.knowledgeview.tablet.arabnews.models.data.ReadingList
import com.knowledgeview.tablet.arabnews.models.data.SectionListing
import com.knowledgeview.tablet.arabnews.models.data.Term
import com.knowledgeview.tablet.arabnews.models.local.DaoAccess
import com.knowledgeview.tablet.arabnews.view.adapters.HomeListingAdapter
import com.knowledgeview.tablet.arabnews.view.adapters.ReadingListAdapter
import com.knowledgeview.tablet.arabnews.viewmodel.HomeListingViewModel
import com.knowledgeview.tablet.arabnews.viewmodel.ReadingListViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var newsDao: DaoAccess
    private var dragPosition: Int = -1
    private var groupPosition: Int = -1
    private var terms: MutableList<Term> = mutableListOf()
    private lateinit var homeListViewModel: HomeListingViewModel
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)
        val progressBar = view.findViewById<ProgressBar>(R.id.pb)
        val readingListFloating = view.findViewById<FloatingActionButton>(R.id.reading_listing_floating)
        readingListFloating.setOnDragListener(dragListen)
        val homeListing = view.findViewById<RecyclerView>(R.id.home_listing)
        homeListing.layoutManager = LinearLayoutManager(context!!,
                RecyclerView.VERTICAL, false)
        homeListViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(HomeListingViewModel::class.java)
        progressBar.visibility = View.VISIBLE
        homeListViewModel.getHomeList().observe(this, Observer { homeList ->
            progressBar.visibility = View.GONE
            if (homeList != null) {
                if (homeList.newsListTop != null)
                    terms.addAll(homeList.newsListTop!!)
                if (homeList.newsList != null)
                    terms.addAll(homeList.newsList!!)
                if (homeList.opinion != null && homeList.opinion!!.isNotEmpty())
                    terms.add(homeList.opinion!![0])
                if (homeList.cartoon != null)
                    terms.add(homeList.cartoon!!)
                val dividerItemDecoration = DividerItemDecoration(context,
                        RecyclerView.VERTICAL)
                homeListing.addItemDecoration(dividerItemDecoration)
                homeListing.adapter = HomeListingAdapter(context!!, terms, newsDao.allTabs)
            }
        })
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
                dragPosition = item.text.toString().split("-")[1].trim().toInt()
                groupPosition = item.text.toString().split("-")[0].trim().toInt()
                true
            }

            DragEvent.ACTION_DRAG_ENDED -> {
                if (event.result) {
                    if (dragPosition != -1) {
                        var type = ""
                        type = when (terms[groupPosition].value) {
                            "opinion" -> "opinion"
                            "cartoon" -> "cartoon"
                            else -> {
                                "section"
                            }
                        }
                        val readingList = ReadingList(terms[groupPosition].data!![dragPosition]
                                , type)
                        newsDao.insertReadingList(readingList)
                    }
                    Toast.makeText(context, "Item added to reading list successfully ", Toast.LENGTH_LONG).show()

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
}