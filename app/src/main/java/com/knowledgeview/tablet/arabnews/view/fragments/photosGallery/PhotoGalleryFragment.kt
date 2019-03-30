package com.knowledgeview.tablet.arabnews.view.fragments.photosGallery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.di.ViewModelFactory
import com.knowledgeview.tablet.arabnews.models.data.HomeData
import com.knowledgeview.tablet.arabnews.utils.ItemDecorationAlbumColumns
import com.knowledgeview.tablet.arabnews.view.NodeDetailsActivity
import com.knowledgeview.tablet.arabnews.view.adapters.PhotosListAdapter
import com.knowledgeview.tablet.arabnews.viewmodel.PhotoGalleryViewModel
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerFragment
import javax.inject.Inject


/**
 * Created by naderbaltaji on 3/23/19
 */
class PhotoGalleryFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var photoGalleryViewModel: PhotoGalleryViewModel

    lateinit var mRvContent: RecyclerView
    lateinit var mViewProgress: ProgressBar
    lateinit var mIvToolbar: ImageView
    lateinit var mTvTitle: TextView
    lateinit var mTvSubtitle: TextView
    lateinit var mTvAuthorName: TextView
    lateinit var mTvViewsCount: TextView
    lateinit var mContainerTitle: LinearLayout

    lateinit var adapter: PhotosListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_photo_gallery, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        photoGalleryViewModel = ViewModelProviders.of(this, viewModelFactory).get(PhotoGalleryViewModel::class.java)

        setupRecycler()
        fetchData()
    }

    private fun initViews(view: View) {
        mRvContent = view.findViewById(R.id.rv_content)
        mViewProgress = view.findViewById(R.id.view_progress)
        mIvToolbar = view.findViewById(R.id.iv_toolbar)
        mTvTitle = view.findViewById(R.id.tv_title)
        mTvSubtitle = view.findViewById(R.id.tv_subtitle)
        mTvAuthorName = view.findViewById(R.id.tv_author_name)
        mTvViewsCount = view.findViewById(R.id.tv_views_count_date)
        mContainerTitle = view.findViewById(R.id.container_title)
    }

    private fun setupRecycler() {
        mRvContent.layoutManager = GridLayoutManager(context, 2)
        mRvContent.addItemDecoration(ItemDecorationAlbumColumns(8, 2))
        adapter = PhotosListAdapter()
        mRvContent.adapter = adapter
    }

    private fun fetchData() {
        mViewProgress.visibility = View.VISIBLE
        photoGalleryViewModel.getPhotoGallery().observe(this, Observer { list->
            mViewProgress.visibility = View.GONE
            val data = list.opinion!!.data
            if (data != null && data.isNotEmpty()) {
                bindTopData(list.opinion!!.data!![0])
                if (data.size > 1) {
                    adapter.addImages(data.subList(1, data.size))
                }
            }
        })
    }

    private fun bindTopData(homeData: HomeData) {
        // image
        val imageUrl = homeData.getPictureLarge()!![0]
        Picasso.get().load(imageUrl)
                .fit()
                .centerCrop()
                .into(mIvToolbar)
        // title
        val label = homeData.getLabel()
        if (label != null) {
            mTvTitle.text = label
        }

        mIvToolbar.setOnClickListener {
            openNodeDetails(homeData.getEntityID())
        }
        mContainerTitle.setOnClickListener {
            openNodeDetails(homeData.getEntityID())
        }
    }

    private fun openNodeDetails(entityId: String) {
        val intent = Intent(context, NodeDetailsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("entityID", entityId)
        startActivity(intent)
    }
}