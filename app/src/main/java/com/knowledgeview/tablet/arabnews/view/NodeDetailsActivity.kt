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
import com.knowledgeview.tablet.arabnews.models.data.Node
import com.knowledgeview.tablet.arabnews.utils.Methods
import com.knowledgeview.tablet.arabnews.view.adapters.BulletListAdapter
import com.knowledgeview.tablet.arabnews.viewmodel.NodeViewModel
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import javax.inject.Inject

class NodeDetailsActivity : AppCompatActivity() {


    private lateinit var nodeViewModel: NodeViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.node_details_activity)
        val image = findViewById<ImageView>(R.id.news_image)
        val author = findViewById<TextView>(R.id.author)
        val date = findViewById<TextView>(R.id.date)
        val bullets = findViewById<RecyclerView>(R.id.bullets)
        bullets.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL
                , false)
        val label = findViewById<TextView>(R.id.headline)
        val categoryName = findViewById<TextView>(R.id.category_name)
        val content = findViewById<WebView>(R.id.content)
        AndroidInjection.inject(this)
        val entityID = intent.getStringExtra("entityID")
        nodeViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(NodeViewModel::class.java)
        nodeViewModel.fetchDetails(entityID)
        nodeViewModel.getNodeDetails().observe(this, Observer { nodes ->
            if (nodes != null && !nodes.data.isNullOrEmpty()) {
                val node = nodes.data[0]
                if (!TextUtils.isEmpty(node.label)) label.text = node.label
                if (!node.categoryName.isNullOrEmpty()) categoryName.text = node.categoryName!![0]
                if (!TextUtils.isEmpty(node.content)) {
                    content.webChromeClient = WebChromeClient()
                    if (!TextUtils.isEmpty(node.content)) {
                        content.settings.javaScriptEnabled = true
                        content.loadDataWithBaseURL("http://ndemo.arabnews.com/", Methods.formatTextWebView(applicationContext,
                                loadHtml(node.content!!)),
                                "text/html", "utf-8", null)
                    }
                    if (!node.images.isNullOrEmpty())
                        Picasso.get().load(node.images!![0]).fit().centerCrop()
                                .into(image)
                    if (!node.author.isNullOrEmpty())
                        author.text = node.author!![0]
                    if (node.date != null)
                        date.text = Methods.dateFormatterString(node.date!!)
                    if (!node.bullets.isNullOrEmpty())
                        bullets.adapter = BulletListAdapter(node.bullets!!,false)
                }
            }
        })
    }

    fun loadHtml(html: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            Html.fromHtml(html);
        }.toString()
    }
}