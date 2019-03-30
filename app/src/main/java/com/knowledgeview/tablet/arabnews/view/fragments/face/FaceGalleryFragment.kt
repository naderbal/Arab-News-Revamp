package com.knowledgeview.tablet.arabnews.view.fragments.face

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.di.ViewModelFactory
import com.knowledgeview.tablet.arabnews.models.data.SectionListing
import com.knowledgeview.tablet.arabnews.models.local.DaoAccess
import com.knowledgeview.tablet.arabnews.utils.ItemDecorationAlbumColumns
import com.knowledgeview.tablet.arabnews.utils.Methods
import com.knowledgeview.tablet.arabnews.view.adapters.FaceListAdapter
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerFragment
import javax.inject.Inject


/**
 * Created by naderbaltaji on 3/23/19
 */
class FaceGalleryFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var mRvContent: RecyclerView
    lateinit var mViewProgress: ProgressBar
    lateinit var mIvToolbar: ImageView
    lateinit var mTvTitle: TextView
    lateinit var mTvSubtitle: TextView
    lateinit var mTvAuthorName: TextView
    lateinit var mTvViewsCount: TextView

    private var news: MutableList<SectionListing> = mutableListOf()
    @Inject
    lateinit var newsDao: DaoAccess
    private var tid: String? = null

    lateinit var adapter: FaceListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_photo_gallery, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)

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
    }

    private fun setupRecycler() {
        mRvContent.layoutManager = GridLayoutManager(context, 2)
        mRvContent.addItemDecoration(ItemDecorationAlbumColumns(8, 2))
        adapter = FaceListAdapter()
        mRvContent.adapter = adapter
    }

    private fun fetchData() {
        val args = arguments
        if (args != null) {
            tid = args.get("tid") as String
            if (!TextUtils.isEmpty(tid)) {
                //p = 0
                news = mutableListOf()
                news = newsDao.getAllListing(tid!!.toInt())

                bindTopData(news[0])
                mViewProgress.visibility = View.GONE
                if (news.size > 1)
                adapter.addImages(news.subList(1, news.size))
                //refreshNews(tid!!)
//
            }
        }
    }

    private fun bindTopData(homeData: SectionListing) {
        // image
        if (!homeData.getPictureSmall().isNullOrEmpty()) {
        val imageUrl = homeData.getPictureSmall()!![0]
            Picasso.get().load(imageUrl)
                    .fit()
                    .centerCrop()
                    .into(mIvToolbar)
        }
        // title
        val label = homeData.getLabel()
        if (label != null) {
            mTvTitle.text = label
        }
        if (homeData.getDate() != null) {
            val formattedDate = Methods.dateFormatterString(homeData.getDate()!!)
            mTvViewsCount.text = formattedDate
        }

    }
}