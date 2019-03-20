package com.knowledgeview.tablet.arabnews.models.networking.apis

import androidx.lifecycle.LiveData
import com.knowledgeview.tablet.arabnews.models.data.ResultData
import com.knowledgeview.tablet.arabnews.vo.ApiResponse
import retrofit2.http.GET

interface GetWebViews {
    @GET("aboutus")
    fun getAboutUs(): LiveData<ApiResponse<ResultData>>
}