package com.knowledgeview.tablet.arabnews.models.networking.apis

import com.knowledgeview.tablet.arabnews.models.data.VideoGalleryListingResult
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PostVideoGalleryList {
    @FormUrlEncoded
    @POST("composed")
    fun fetchVideoGallery(@Field("widget") widget: String): Call<VideoGalleryListingResult>
}