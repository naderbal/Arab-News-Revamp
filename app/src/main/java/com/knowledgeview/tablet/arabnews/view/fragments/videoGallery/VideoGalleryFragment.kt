package com.knowledgeview.tablet.arabnews.view.fragments.videoGallery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.di.ViewModelFactory
import com.knowledgeview.tablet.arabnews.models.data.HomeData
import com.knowledgeview.tablet.arabnews.utils.ItemDecorationAlbumColumns
import com.knowledgeview.tablet.arabnews.view.VideoDetailsActivity
import com.knowledgeview.tablet.arabnews.view.adapters.NewsHeaderAdapter
import com.knowledgeview.tablet.arabnews.view.adapters.PhotosListAdapter
import com.knowledgeview.tablet.arabnews.view.adapters.VideoListingAdapter
import com.knowledgeview.tablet.arabnews.viewmodel.PhotoGalleryViewModel
import com.knowledgeview.tablet.arabnews.viewmodel.VideoGalleryViewModel
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerFragment
import javax.inject.Inject


/**
 * Created by naderbaltaji on 3/23/19
 */
class VideoGalleryFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var photoGalleryViewModel: VideoGalleryViewModel

    lateinit var mRvContent: RecyclerView
    lateinit var mViewProgress: ProgressBar

    lateinit var adapter: VideoListingAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_video_gallery, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        photoGalleryViewModel = ViewModelProviders.of(this, viewModelFactory).get(VideoGalleryViewModel::class.java)

        setupRecycler()
        fetchData()
    }

    private fun initViews(view: View) {
        mRvContent = view.findViewById(R.id.rv_content)
        mViewProgress = view.findViewById(R.id.view_progress)
    }

    private fun setupRecycler() {
        mRvContent.layoutManager = LinearLayoutManager(context)
        mRvContent.addItemDecoration(ItemDecorationAlbumColumns(8, 2))
    }

    private fun fetchData() {
        mViewProgress.visibility = View.VISIBLE
        photoGalleryViewModel.getVideoGallery().observe(this, Observer { list->
            mViewProgress.visibility = View.GONE
            val data = list.opinion!!.data
            if (data != null) {
                adapter = VideoListingAdapter(context!!, data, object : VideoListingAdapter.VideoGalleryListener {
                    override fun onVideoItemClicked(video: HomeData) {
                        val intent = Intent(context, VideoDetailsActivity::class.java)
                        intent.putExtra("entityID", video.getEntityID())
                        startActivity(intent)
                    }

                })
                mRvContent.adapter = adapter
            }
        })
    }
}