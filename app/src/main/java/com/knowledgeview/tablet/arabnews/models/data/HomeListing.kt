package com.knowledgeview.tablet.arabnews.models.data

import com.google.gson.annotations.SerializedName

class HomeListing {
    @SerializedName("api_widget_opinion")
    var opinion: List<Term>? = null
    @SerializedName("api_widget_cartoon")
    var cartoon: Term? = null
    @SerializedName("api_term_page")
    var newsList:List<Term>?=null
    @SerializedName("api_widget_top")
    var newsListTop:List<Term>?=null

}