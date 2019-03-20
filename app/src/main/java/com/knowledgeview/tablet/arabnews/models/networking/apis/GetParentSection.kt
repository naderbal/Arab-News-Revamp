package com.knowledgeview.tablet.arabnews.models.networking.apis

import androidx.lifecycle.LiveData
import com.knowledgeview.tablet.arabnews.models.data.SectionResult
import com.knowledgeview.tablet.arabnews.vo.ApiResponse
import retrofit2.http.GET

interface GetParentSection {
    @GET("menu")
    fun getSectionsResult(): LiveData<ApiResponse<SectionResult>>
}