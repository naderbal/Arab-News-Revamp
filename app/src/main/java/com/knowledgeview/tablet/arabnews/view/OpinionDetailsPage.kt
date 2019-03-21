package com.knowledgeview.tablet.arabnews.view

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.di.ViewModelFactory
import com.knowledgeview.tablet.arabnews.utils.Methods
import com.knowledgeview.tablet.arabnews.view.adapters.BulletListAdapter
import com.knowledgeview.tablet.arabnews.view.adapters.OpinionListAdapter
import com.knowledgeview.tablet.arabnews.viewmodel.NodeViewModel
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import javax.inject.Inject

class OpinionDetailsPage : AppCompatActivity() {

    private lateinit var nodeViewModel: NodeViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.opinion_details_page)
        val image = findViewById<ImageView>(R.id.news_image)
        val author = findViewById<TextView>(R.id.author_name)
        val date = findViewById<TextView>(R.id.date)
        val bullets = findViewById<RecyclerView>(R.id.bullets)
        bullets.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL
                , false)
        val authorImage = findViewById<ImageView>(R.id.author_image)
        val label = findViewById<TextView>(R.id.headline)
        val content = findViewById<WebView>(R.id.content)
        val opinionList = findViewById<RecyclerView>(R.id.opinion_list)
        opinionList.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL,
                false)
        AndroidInjection.inject(this)
        val entityID = intent.getStringExtra("entityID")
        nodeViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(NodeViewModel::class.java)
        //change author id here
        nodeViewModel.fetchOpinionDetails(entityID, "5771")
        nodeViewModel.getOpinionDetails().observe(this, Observer { nodes ->
            if (nodes != null && !nodes.data.isNullOrEmpty()) {
                val node = nodes.data[0]
                if (!TextUtils.isEmpty(node.label)) label.text = node.label
                if (!TextUtils.isEmpty(node.content)) {
                    content.webChromeClient = WebChromeClient()
                    if (!TextUtils.isEmpty(node.content)) {
                        content.settings.javaScriptEnabled = true
                        content.loadDataWithBaseURL("http://www.arabnews.com/", Methods.formatTextWebView(applicationContext,
                                loadHtml(node.content!!)),
                                "text/html", "utf-8", null)
                    }
                    if (!node.images.isNullOrEmpty())
                        Picasso.get().load(node.images!![0]).fit().centerCrop()
                                .into(image)
                    if (node.authorObject != null) {
                        if (!node.authorObject!!.name.isNullOrEmpty())
                            author.text = node.authorObject!!.name
                        if (!node.authorObject!!.authorImage.isNullOrEmpty())
                            Picasso.get().load(node.authorObject!!.authorImage)
                                    .fit().centerCrop().into(authorImage)
                    }
                    if (node.date != null)
                        date.text = Methods.dateFormatterString(node.date!!)
                    if (!node.bullets.isNullOrEmpty())
                        bullets.adapter = BulletListAdapter(node.bullets!!, false)
                    if (!node.opinionList.isNullOrEmpty())
                        opinionList.adapter = OpinionListAdapter(applicationContext, node.opinionList!!, 0
                                , false)
                }
            }
        })
    }

    private fun loadHtml(html: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            Html.fromHtml(html);
        }.toString()
    }
}