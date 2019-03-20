package com.knowledgeview.tablet.arabnews.models.data

import com.google.gson.annotations.SerializedName

class NodeDetailsResult {

    @SerializedName("api_node_page")
    var nodeDetails: List<Node>? = null
}