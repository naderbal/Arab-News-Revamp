package com.knowledgeview.tablet.arabnews.models.networking.apis

import com.knowledgeview.tablet.arabnews.models.data.AuthorsListingResult
import com.knowledgeview.tablet.arabnews.models.data.HomeListingResult
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface PostOpinionsList {
    @FormUrlEncoded
    @POST("composed")
    fun fetchOpinionsList(@Field("multiterm") terms: String, @Field("widget") widget: String)
            : Call<HomeListingResult>

    @GET("sections/authors")
    fun fetchAuthorsList(): Call<AuthorsListingResult>
}