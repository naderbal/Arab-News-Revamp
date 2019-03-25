package com.knowledgeview.tablet.arabnews.models.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.knowledgeview.tablet.arabnews.models.data.PhotoGalleryListing
import com.knowledgeview.tablet.arabnews.models.data.PhotoGalleryListingResult
import com.knowledgeview.tablet.arabnews.models.data.VideoGalleryListing
import com.knowledgeview.tablet.arabnews.models.data.VideoGalleryListingResult
import com.knowledgeview.tablet.arabnews.models.networking.apis.PostPhotoGalleryList
import com.knowledgeview.tablet.arabnews.models.networking.apis.PostVideoGalleryList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by naderbaltaji on 3/23/19
 */
class VideoGalleryRepository @Inject constructor(val postPhotoOpinionsList: PostVideoGalleryList) {

    fun getPhotoGallery(): LiveData<VideoGalleryListing> {
        val data = MutableLiveData<VideoGalleryListing>()
        postPhotoOpinionsList.fetchVideoGallery("video")
                .enqueue(object : Callback<VideoGalleryListingResult> {
                    override fun onFailure(call: Call<VideoGalleryListingResult>, t: Throwable) {
                        data.value = null
                    }

                    override fun onResponse(call: Call<VideoGalleryListingResult>, response: Response<VideoGalleryListingResult>) {
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