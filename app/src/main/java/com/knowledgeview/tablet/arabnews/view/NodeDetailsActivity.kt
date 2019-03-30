package com.knowledgeview.tablet.arabnews.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.di.ViewModelFactory
import com.knowledgeview.tablet.arabnews.models.data.Node
import com.knowledgeview.tablet.arabnews.utils.Methods
import com.knowledgeview.tablet.arabnews.view.adapters.BulletListAdapter
import com.knowledgeview.tablet.arabnews.view.ui.FontSize
import com.knowledgeview.tablet.arabnews.viewmodel.NodeViewModel
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import javax.inject.Inject

class NodeDetailsActivity : AppCompatActivity() {


    private lateinit var nodeViewModel: NodeViewModel

    lateinit var ivShare: ImageView
    lateinit var ivFontSize: ImageView
    lateinit var content : WebView
    lateinit var author : TextView
    lateinit var date : TextView

    var sharableLink: String? = null

    var fontSize = FontSize.BIG

    var node : Node? = null

    var bulletListAdapter : BulletListAdapter? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.node_details_activity)
        configureToolbarWithUpButton(R.id.toolbar)

        ivShare = findViewById(R.id.iv_share)
        ivFontSize = findViewById(R.id.iv_font_size)
        content = findViewById(R.id.content)
        author = findViewById(R.id.author)
        date = findViewById(R.id.date)
        val image = findViewById<ImageView>(R.id.news_image)
        val bullets = findViewById<RecyclerView>(R.id.bullets)

        bullets.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL
                , false)
        val label = findViewById<TextView>(R.id.headline)
        val categoryName = findViewById<TextView>(R.id.category_name)

        AndroidInjection.inject(this)
        val entityID = intent.getStringExtra("entityID")

        nodeViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(NodeViewModel::class.java)

        nodeViewModel.fetchDetails(entityID)

        nodeViewModel.getNodeDetails().observe(this, Observer { nodes ->
            if (nodes != null && !nodes.data.isNullOrEmpty()) {
                node = nodes.data[0]
                sharableLink = node!!.link
                if (!TextUtils.isEmpty(node!!.label)) label.text = node!!.label
                if (!node!!.categoryName.isNullOrEmpty()) categoryName.text = node!!.categoryName!![0]
                if (!TextUtils.isEmpty(node!!.content)) {
                    content.webChromeClient = WebChromeClient()
                    if (!TextUtils.isEmpty(node!!.content)) {
                        loadWebView(0)
                    }
                }
                if (!node!!.images.isNullOrEmpty())
                    Picasso.get().load(node!!.images!![0]).fit().centerCrop()
                            .into(image)
                if (!node!!.author.isNullOrEmpty()) {
                    author.visibility = View.VISIBLE
                    author.text = node!!.author!![0]
                } else{
                    author.visibility = View.GONE
                }
                if (node!!.date != null)
                    date.text = Methods.dateFormatterString(node!!.date!!)
                if (!node!!.bullets.isNullOrEmpty()) {
                    bulletListAdapter = BulletListAdapter(node!!.bullets!!, false)
                    bullets.adapter = bulletListAdapter
                }
            }

        })

        ivShare.setOnClickListener {
            if (sharableLink != null) {
                val sharingIntent = Intent(Intent.ACTION_SEND)
                sharingIntent.type = "text/plain"
                val shareBody = sharableLink
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody)
                startActivity(Intent.createChooser(sharingIntent, "Share"))
            }
        }

        ivFontSize.setOnClickListener {
            if (fontSize == FontSize.BIG) {
                loadWebView(-30)
                setFieldTextSize(-6)
                fontSize = FontSize.SMALL
            } else if (fontSize == FontSize.NORMAL){
                loadWebView(+15)
                setFieldTextSize(+3)
                fontSize = FontSize.BIG
            } else {
                loadWebView(+15)
                setFieldTextSize(+3)
                fontSize = FontSize.NORMAL
            }
        }
    }

    private fun setFieldTextSize(sizeDiffernce: Int) {
        val textSize = Methods.pixelsToSp(this, author.textSize) + sizeDiffernce
        author.textSize = textSize
        val dateTextSize = Methods.pixelsToSp(this, date.textSize) + sizeDiffernce
        date.textSize = dateTextSize
        bulletListAdapter?.updateTextSize(sizeDiffernce)
    }

    private fun loadWebView(zoomLevel: Int) {
        val settings = content.settings
        if(zoomLevel != 0) settings.textZoom = settings.getTextZoom() + zoomLevel
        content.setBackgroundColor(Color.argb(1, 0, 0, 0))// for flickering
        content.loadDataWithBaseURL("http://www.arabnews.com/", Methods.formatTextWebView(applicationContext,
                loadHtml(node?.content!!)),
                "text/html", "utf-8", null)
    }

    fun loadHtml(html: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html);
        }.toString()
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