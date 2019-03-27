package com.knowledgeview.tablet.arabnews.utils

import android.content.Context
import android.text.Html
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Miriana on 2/12/2018.
 */
class Methods {
    companion object {
        fun dateFormatterString(date: Date): String {
            val df = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
            return df.format(date)
        }

        fun formatTextWebView(context: Context, content: String): String {
            var html = context.assets.open("news_detail.html").bufferedReader().use {
                it.readText()
            }
            html = html.replace("{{description}}", content)
            return html
        }

        fun pixelsToSp(context: Context, px: Float): Float {
            val scaledDensity = context.resources.displayMetrics.scaledDensity
            return px / scaledDensity
        }
    }
}