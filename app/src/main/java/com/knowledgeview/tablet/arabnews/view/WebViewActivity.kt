package com.knowledgeview.tablet.arabnews.view

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.knowledgeview.tablet.arabnews.R

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_view_activity)
        val webView = findViewById<WebView>(R.id.web_view)
        val type = intent.getStringExtra("type")
        webView.webChromeClient = WebChromeClient()
        /*if (!TextUtils.isEmpty(news.content))
            content.loadDataWithBaseURL("https://www.hiamag.com",
                    Methods.formatTextWebView(applicationContext, news.content!!), "text/html", "utf-8", null)*/
    }
}