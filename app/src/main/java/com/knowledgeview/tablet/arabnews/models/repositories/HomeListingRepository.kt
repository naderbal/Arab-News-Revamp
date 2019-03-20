package com.knowledgeview.tablet.arabnews.models.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.knowledgeview.tablet.arabnews.AppExecutors
import com.knowledgeview.tablet.arabnews.models.data.HomeListing
import com.knowledgeview.tablet.arabnews.models.data.HomeListingResult
import com.knowledgeview.tablet.arabnews.models.local.DaoAccess
import com.knowledgeview.tablet.arabnews.models.networking.apis.PostHomeData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class HomeListingRepository @Inject constructor(
        private val postHomeData: PostHomeData
) {


    fun getHomeScreenData(): LiveData<HomeListing> {
        val data = MutableLiveData<HomeListing>()
        postHomeData.fetchHomeData("1,2,3,8", "opinion,cartoon,main,top4")
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