package com.knowledgeview.tablet.arabnews.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.di.ViewModelFactory
import com.knowledgeview.tablet.arabnews.view.fragments.opinions.listing.OpinionsAuthorHeaderListingItem
import com.knowledgeview.tablet.arabnews.view.fragments.opinions.listing.OpinionsListingAdapter
import com.knowledgeview.tablet.arabnews.view.fragments.opinions.listing.OpinionsListingItem
import com.knowledgeview.tablet.arabnews.view.fragments.opinions.listing.OpinionsOpinionsListingItem
import com.knowledgeview.tablet.arabnews.viewmodel.AuthorOpinionsListingViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject


/**
 * Created by naderbaltaji on 3/24/19
 */
class AuthorDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: AuthorOpinionsListingViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var mRvContent: RecyclerView
    lateinit var mViewProgress: ProgressBar
    lateinit var mTvAuthorName: TextView
    lateinit var adapter: OpinionsListingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_author_details)
        configureToolbarWithUpButton(R.id.toolbar)

        mRvContent = findViewById(R.id.rv_content)
        mViewProgress = findViewById(R.id.view_progress)
        mTvAuthorName = findViewById(R.id.tv_author_name)

        setupRecycler()
        AndroidInjection.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(AuthorOpinionsListingViewModel::class.java)

        val authorId = intent.getStringExtra("author_id")
        val authorName = intent.getStringExtra("author_name")

        mTvAuthorName.text = authorName
        mViewProgress.visibility = View.VISIBLE
        viewModel.fetchAuthorOpinionsList(authorId)
        viewModel.getOpinionsList().observe(this, Observer {list ->
            mViewProgress.visibility = View.GONE
            val opinionList = list.opinion
            val listingItems = ArrayList<OpinionsListingItem>()
            if (opinionList != null && opinionList.isNotEmpty()) {
                var term = opinionList[0] // top opinion list
                val firstHome = term.data!![0]
                listingItems.add(OpinionsAuthorHeaderListingItem(firstHome))
                listingItems.add(OpinionsOpinionsListingItem(term.data!!))
            }
            adapter.addItems(listingItems)
        })
    }

    private fun setupRecycler() {
        mRvContent.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = OpinionsListingAdapter(null)
        mRvContent.adapter = adapter
        mRvContent.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    fun configureToolbarWithUpButton(toolbarId: Int) {
        val toolbar = findViewById<Toolbar>(toolbarId)
        // set toolbar
        setSupportActionBar(toolbar)
        // add click listener on toolbar back arrow
        toolbar.setNavigationOnClickListener { view -> onBackPressed() }
        val supportActionBar = supportActionBar
        // configure back action
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false)
            supportActionBar.setDisplayHomeAsUpEnabled(true)
            supportActionBar.setDisplayShowHomeEnabled(true)
        }
    }
}