package com.knowledgeview.tablet.arabnews.utils

import android.text.Html
import android.text.Spanned



/**
 * Created by Miriana on 1/26/2018.
 */
open class FormatHtml {
    companion object {

        fun formatHtml(input: String): Spanned {
            return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(input)
            }
        }
    }
}