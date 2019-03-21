package com.knowledgeview.tablet.arabnews.models.networking.apis

import androidx.lifecycle.LiveData
import com.knowledgeview.tablet.arabnews.models.data.HomeListingResult
import com.knowledgeview.tablet.arabnews.models.data.NodeDetailsMainResult
import com.knowledgeview.tablet.arabnews.vo.ApiResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PostOpinionDetails {

    @FormUrlEncoded
    @POST("composed")
    fun fetchOpinionData(@Field("widget") opinionOfAuthor: String, @Field("node") node: String)
            : LiveData<ApiResponse<NodeDetailsMainResult>>
}