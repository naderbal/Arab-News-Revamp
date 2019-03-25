package com.knowledgeview.tablet.arabnews.models.networking.apis

import com.knowledgeview.tablet.arabnews.models.data.PhotoGalleryListingResult
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PostPhotoGalleryList {
    @FormUrlEncoded
    @POST("composed")
    fun fetchPhotoGallery(@Field("widget") widget: String): Call<PhotoGalleryListingResult>
}