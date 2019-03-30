package com.knowledgeview.tablet.arabnews.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
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
import com.knowledgeview.tablet.arabnews.view.adapters.OpinionListAdapter
import com.knowledgeview.tablet.arabnews.view.ui.FontSize
import com.knowledgeview.tablet.arabnews.viewmodel.OpinionDetailsViewModel
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import javax.inject.Inject


class OpinionDetailsActivity : AppCompatActivity() {

    private lateinit var opinionViewModel: OpinionDetailsViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    // view
    lateinit var ivShare: ImageView
    lateinit var ivFontSize: ImageView
    lateinit var content : WebView
    lateinit var author : TextView
    lateinit var date : TextView

    // data
    var fontSize = FontSize.BIG
    var sharableLink: String? = null
    var node : Node? = null

    var bulletListAdapter : BulletListAdapter? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.opinion_details_page)
        configureToolbarWithUpButton(R.id.toolbar)

        val bullets = findViewById<RecyclerView>(R.id.bullets)

        ivShare = findViewById(R.id.iv_share)
        ivFontSize = findViewById(R.id.iv_font_size)
        content = findViewById(R.id.content)
        author = findViewById(R.id.author_name)
        date = findViewById(R.id.date)

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
        val authorId = intent.getStringExtra("authorID")
        opinionViewModel = ViewModelProviders.of(this, viewModelFactory).get(OpinionDetailsViewModel::class.java)
        //change author id here
        opinionViewModel.fetchOpinionDetails(entityID, authorId)
        opinionViewModel.getOpinionDetails().observe(this, Observer { nodes ->
            if (nodes?.data != null) {
                node = nodes.data
                sharableLink = node!!.link

                if (!TextUtils.isEmpty(node!!.label)) label.text = node!!.label
                if (!TextUtils.isEmpty(node!!.content)) {
                    content.webChromeClient = WebChromeClient()
                    if (!TextUtils.isEmpty(node!!.content)) {
                        content.settings.javaScriptEnabled = true
                        content.loadDataWithBaseURL("http://www.arabnews.com/", Methods.formatTextWebView(applicationContext,
                                loadHtml(node!!.content!!)),
                                "text/html", "utf-8", null)
                    }
                    if (node!!.authorObject != null) {
                        if (!node!!.authorObject!!.name.isNullOrEmpty())
                            author.text = node!!.authorObject!!.name
                        if (!node!!.authorObject!!.authorImage.isNullOrEmpty())
                            Picasso.get().load(node!!.authorObject!!.authorImage)
                                    .fit().centerCrop().into(authorImage)
                    }
                    if (node!!.date != null)
                        date.text = Methods.dateFormatterString(node!!.date!!)
                    if (!node!!.bullets.isNullOrEmpty())
                        bullets.adapter = BulletListAdapter(node!!.bullets!!, false)
                    if (!node!!.opinionList.isNullOrEmpty())
                        bulletListAdapter = BulletListAdapter(node!!.bullets!!, false)
                        opinionList.adapter = bulletListAdapter
                }
            }
        })

        ivShare.setOnClickListener {
            if (sharableLink != null) {
                val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
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
    private fun loadHtml(html: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
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