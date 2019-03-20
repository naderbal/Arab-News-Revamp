package com.knowledgeview.tablet.arabnews.models.networking.apis

import androidx.lifecycle.LiveData
import com.knowledgeview.tablet.arabnews.models.data.NodeDetailsMainResult
import com.knowledgeview.tablet.arabnews.vo.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GetNodeDetails {

    @GET("node/{nodeID}")
    fun getNodeDetails(@Path("nodeID") nodeID: String): LiveData<ApiResponse<NodeDetailsMainResult>>
}