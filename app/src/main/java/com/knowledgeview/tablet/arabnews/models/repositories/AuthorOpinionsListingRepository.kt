package com.knowledgeview.tablet.arabnews.models.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.knowledgeview.tablet.arabnews.models.data.*
import com.knowledgeview.tablet.arabnews.models.networking.apis.PostAuthorOpinionsList
import com.knowledgeview.tablet.arabnews.models.networking.apis.PostOpinionsList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthorOpinionsListingRepository @Inject constructor(
        private val postOpinions: PostAuthorOpinionsList
) {


    fun getAuthorsOpinionsData(authorId: String): LiveData<HomeListing> {
        val data = MutableLiveData<HomeListing>()
        postOpinions.fetchOpinionsList( "opinion_ofauthor:$authorId, opinion")
                .enqueue(object : Callback<HomeListingResult> {
                    override fun onFailure(call: Call<HomeListingResult>, t: Throwable) {
                        data.value = null
                    }

                    override fun onResponse(call: Call<HomeListingResult>, response: Response<HomeListingResult>) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            if (responseBody.data != null) {
                                data.value = responseBody.data
                            }
                        }
                    }

                })
        return data
    }
}