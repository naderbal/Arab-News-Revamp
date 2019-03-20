package com.knowledgeview.tablet.arabnews.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.di.ViewModelFactory
import com.knowledgeview.tablet.arabnews.models.data.ReadingList
import com.knowledgeview.tablet.arabnews.models.local.DaoAccess
import com.knowledgeview.tablet.arabnews.utils.RecyclerItemClickListener
import com.knowledgeview.tablet.arabnews.view.NodeDetailsActivity
import com.knowledgeview.tablet.arabnews.view.adapters.ReadingListAdapter
import com.knowledgeview.tablet.arabnews.viewmodel.ReadingListViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class ReadingListFragment : DaggerFragment() {

    private val readingList = mutableListOf<ReadingList>()

    @Inject
    lateinit var newsDao: DaoAccess

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var readingListViewModel: ReadingListViewModel
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.reading_list_fragment, container, false)
        val readingListView = view.findViewById<RecyclerView>(R.id.reading_list)
        val dividerItemDecoration = DividerItemDecoration(context,
                RecyclerView.VERTICAL)
        readingListView.addItemDecoration(dividerItemDecoration)
        val readingListError = view.findViewById<TextView>(R.id.reading_list_error)
        readingListView.layoutManager = LinearLayoutManager(context!!, RecyclerView.VERTICAL, false)
        readingListViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ReadingListViewModel::class.java)
        readingListViewModel.getReadingList().observe(this, Observer { sections ->
            readingList.clear()
            if (sections != null) {
                readingList.addAll(sections)
                readingListView.adapter = ReadingListAdapter(context!!,sections)
            }
            if (readingList.size > 0) readingListError.visibility = View.GONE
            else readingListError.visibility = View.VISIBLE
        })
        val simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                newsDao.removeReadingList(readingList[viewHolder.adapterPosition].getEntityID())
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(readingListView)
        /*readingListView.addOnItemTouchListener(RecyclerItemClickListener(context!!,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(childView: View, position: Int) {
                        val intent = Intent(context!!,NodeDetailsActivity::class.java)
                        intent.putExtra("entityID",readingList[position].getEntityID())
                        startActivity(intent)
                    }

                    override fun onItemLongPress(childView: View, position: Int) {
                    }

                }))*/
        return view
    }

}