package com.knowledgeview.tablet.arabnews.models.networking.apis

import com.knowledgeview.tablet.arabnews.models.data.HomeListingResult
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PostAuthorOpinionsList {
    @FormUrlEncoded
    @POST("composed")
    fun fetchOpinionsList(@Field("widget") widget: String)
            : Call<HomeListingResult>
}