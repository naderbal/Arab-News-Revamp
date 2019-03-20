package com.knowledgeview.tablet.arabnews.models.data

import com.google.gson.annotations.SerializedName

class Term {
    var data: List<HomeData>? = null
    @SerializedName("qvalue")
    var value: String? = null
}