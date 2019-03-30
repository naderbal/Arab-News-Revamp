package com.knowledgeview.tablet.arabnews.view

import android.content.Intent
import android.net.Uri
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
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.di.ViewModelFactory
import com.knowledgeview.tablet.arabnews.utils.Methods
import com.knowledgeview.tablet.arabnews.view.adapters.BulletListAdapter
import com.knowledgeview.tablet.arabnews.viewmodel.NodeViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject


/**
 * Created by naderbaltaji on 3/24/19
 */
class VideoDetailsActivity : AppCompatActivity() {

    private lateinit var nodeViewModel: NodeViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var ivShare: ImageView

    var sharableLink: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_details)
        configureToolbarWithUpButton(R.id.toolbar)
        ivShare = findViewById(R.id.iv_share)

        val youTubePlayerFragment = initYouTubePlayer()
        val author = findViewById<TextView>(R.id.author)
        val date = findViewById<TextView>(R.id.date)
        val bullets = findViewById<RecyclerView>(R.id.bullets)
        val content = findViewById<WebView>(R.id.content)
        bullets.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL
                , false)
        val label = findViewById<TextView>(R.id.headline)
        AndroidInjection.inject(this)
        val entityID = intent.getStringExtra("entityID")
        nodeViewModel = ViewModelProviders.of(this, viewModelFactory).get(NodeViewModel::class.java)
        nodeViewModel.fetchDetails(entityID)
        nodeViewModel.getNodeDetails().observe(this, Observer { nodes ->
            if (nodes != null && !nodes.data.isNullOrEmpty()) {
                val node = nodes.data[0]
                sharableLink = node.link
                content.webChromeClient = WebChromeClient()
                if (!TextUtils.isEmpty(node.content)) {
                    content.settings.javaScriptEnabled = true
                    content.loadDataWithBaseURL("http://www.arabnews.com/",
                            Methods.formatTextWebView(applicationContext, loadHtml(node.content!!)),
                            "text/html", "utf-8", null)
                }
                if (!TextUtils.isEmpty(node.label)) label.text = node.label
                if (!node.author.isNullOrEmpty())
                    author.text = node.author!![0]
                if (node.date != null)
                    date.text = Methods.dateFormatterString(node.date!!)
                if (!node.bullets.isNullOrEmpty())
                    bullets.adapter = BulletListAdapter(node.bullets!!,false)
                youTubePlayerFragment!!.initialize("AIzaSyDmKsJoRPn6MgzCY8nSYXYI7cW4mLKACNw",
                        object : YouTubePlayer.OnInitializedListener {
                            override fun onInitializationSuccess(provider: YouTubePlayer.Provider,
                                                                 youTubePlayer: YouTubePlayer, b: Boolean) {
                                if(!node.videoUrl.isNullOrEmpty()){
                                    youTubePlayer.cueVideo(getQueryParameter(node.videoUrl!![0]))
                                }
                            }

                            override fun onInitializationFailure(provider: YouTubePlayer.Provider,
                                                                 youTubeInitializationResult: YouTubeInitializationResult) {

                            }
                        })
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
    }

    private fun initYouTubePlayer(): YouTubePlayerSupportFragment? {
        val youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.youtube_fragment, youTubePlayerFragment).commit()
        return youTubePlayerFragment
    }

    fun getQueryParameter(url: String) : String? {
        val uri = Uri.parse(url)
        return uri.getQueryParameter("v")
    }

    fun loadHtml(html: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
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