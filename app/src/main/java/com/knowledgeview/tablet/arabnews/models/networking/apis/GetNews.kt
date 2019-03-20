package com.knowledgeview.tablet.arabnews.models.networking.apis

import com.knowledgeview.tablet.arabnews.models.data.SectionListingResult
import com.knowledgeview.tablet.arabnews.models.data.VideoListing
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GetNews {
    @GET("section/{sectionID}/10/{page}")
    fun getNewsListing(@Path("sectionID") sectionID: String,
                       @Path("page") page: String): Call<SectionListingResult>

    @GET("section/{sectionID}/10/{page}")
    fun getVideos(@Path("sectionID") sectionID: String,
                       @Path("page") page: String): Call<VideoListing>
}