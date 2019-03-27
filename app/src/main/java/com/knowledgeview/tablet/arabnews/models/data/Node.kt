package com.knowledgeview.tablet.arabnews.models.data

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*


@Entity
class Node {

    @PrimaryKey
    @NonNull
    @SerializedName("entity_id")
    var entityID: String = ""
    var label: String? = null
    var content: String? = null
    @SerializedName("category_name")
    var categoryName: List<String>? = null
    @SerializedName("picture_small")
    var images: List<String>? = null
    @SerializedName("sm_field_bullets")
    var bullets: List<String>? = null
    @SerializedName("sm_vid_Authors")
    var author: List<String>? = null
    @SerializedName("ds_created")
    var date: Date? = null
    @SerializedName("author_details")
    var authorObject: Author? = null
    @SerializedName("opinion_list")
    var opinionList:List<HomeData>?=null
    @SerializedName("sm_field_video_url")
    var videoUrl:List<String>?=null
    @SerializedName("link")
    var link: String? = null
}