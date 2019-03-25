package com.knowledgeview.tablet.arabnews.view.fragments.opinions

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.di.ViewModelFactory
import com.knowledgeview.tablet.arabnews.models.data.Author
import com.knowledgeview.tablet.arabnews.models.local.DaoAccess
import com.knowledgeview.tablet.arabnews.view.AuthorDetailsActivity
import com.knowledgeview.tablet.arabnews.view.fragments.opinions.listing.*
import com.knowledgeview.tablet.arabnews.viewmodel.OpinionsListingViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Created by naderbaltaji on 3/22/19
 */
class OpinionsListFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var newsDao: DaoAccess
    private lateinit var opinionsListViewModel: OpinionsListingViewModel

    lateinit var mRvContent: RecyclerView
    lateinit var mViewProgress: ProgressBar

    lateinit var adapter: OpinionsListingAdapter

    val listingItems = ArrayList<OpinionsListingItem>()

    var isAuthorsListReceived = false
    var isOpinionsListReceived = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_opinions_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRvContent = view.findViewById(R.id.rv_content)
        mViewProgress = view.findViewById(R.id.view_progress)
        opinionsListViewModel = ViewModelProviders.of(this, viewModelFactory).get(OpinionsListingViewModel::class.java)
        setupRecycler()

        fetchData()
    }

    private fun fetchData() {
        mViewProgress.visibility = View.VISIBLE
        opinionsListViewModel.getOpinionsList().observe(this, Observer { list ->
            mViewProgress.visibility = View.GONE
            val opinionList = list.opinion
            if (opinionList != null && opinionList.size > 1) {
                var term = opinionList[1] // top opinion list
                listingItems.add(OpinionsHeaderListinItem(getString(R.string.top_opinion_articles)))
                listingItems.add(OpinionsOpinionsListingItem(term.data!!))
            }
            list.cartoon?.let {
                listingItems.add(OpinionsHeaderListinItem(getString(R.string.cartoon)))
                list.cartoon?.let {
                    listingItems.add(OpinionsCartoonListingItem(it.data!!))
                }
            }
            if (opinionList != null && opinionList.isNotEmpty()) {
                var term = opinionList[0] // more opinion list
                listingItems.add(OpinionsHeaderListinItem(getString(R.string.more)))
                listingItems.add(OpinionsOpinionsListingItem(term.data!!))
            }
            isOpinionsListReceived = true
            if (isAuthorsListReceived){
                adapter.addItems(listingItems)
            }
        })
        opinionsListViewModel.getAuthorsList().observe(this, Observer { items ->
            this.listingItems.add(0, OpinionsHeaderListinItem(getString(R.string.authors)))
            val authorsListingItem = OpinionsAuthorsListingItem(items)
            this.listingItems.add(1, authorsListingItem)
            isAuthorsListReceived = true
            if (isOpinionsListReceived) {
                adapter.addItems(listingItems)
            }
        })
    }

    private fun setupRecycler() {
        mRvContent.layoutManager = LinearLayoutManager(context)
        adapter = OpinionsListingAdapter(object : OpinionsListListener {
            override fun onAuthorClicked(author: Author) {
                val intent = Intent(context, AuthorDetailsActivity::class.java)
                intent.putExtra("author_id", author.tid)
                intent.putExtra("author_name", author.name)
                startActivity(intent)
            }
        })
        mRvContent.adapter = adapter
    }
}