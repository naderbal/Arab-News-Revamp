package com.knowledgeview.tablet.arabnews.models.data

import com.google.gson.annotations.SerializedName

class SectionListingModel {
    @SerializedName("api_term_page")
    var sectionListing:MutableList<SectionListing>?=null
}