package com.knowledgeview.tablet.arabnews.models.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*


@Entity
class Node {

    @PrimaryKey
    @SerializedName("entity_id")
    var entityID: String = ""
    var label: String? = null
    var content: String? = null
    @SerializedName("category_name")
    var categoryName: List<String>? = null
    @SerializedName("sm_field_binary")
    var images: List<String>? = null
    @SerializedName("sm_field_bullets")
    var bullets: List<String>? = null
    @SerializedName("sm_vid_Authors")
    var author: List<String>? = null
    @SerializedName("ds_created")
    var date: Date? = null
}