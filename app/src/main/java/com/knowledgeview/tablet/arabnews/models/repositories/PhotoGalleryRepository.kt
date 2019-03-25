package com.knowledgeview.tablet.arabnews.models.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.knowledgeview.tablet.arabnews.models.data.PhotoGalleryListing
import com.knowledgeview.tablet.arabnews.models.data.PhotoGalleryListingResult
import com.knowledgeview.tablet.arabnews.models.networking.apis.PostPhotoGalleryList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by naderbaltaji on 3/23/19
 */
class PhotoGalleryRepository @Inject constructor(val postPhotoOpinionsList: PostPhotoGalleryList) {

    fun getPhotoGallery(): LiveData<PhotoGalleryListing> {
        val data = MutableLiveData<PhotoGalleryListing>()
        postPhotoOpinionsList.fetchPhotoGallery("gallery")
                .enqueue(object : Callback<PhotoGalleryListingResult> {
                    override fun onFailure(call: Call<PhotoGalleryListingResult>, t: Throwable) {
                        data.value = null
                    }

                    override fun onResponse(call: Call<PhotoGalleryListingResult>, response: Response<PhotoGalleryListingResult>) {
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