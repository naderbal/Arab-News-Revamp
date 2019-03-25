package com.knowledgeview.tablet.arabnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.knowledgeview.tablet.arabnews.models.data.VideoGalleryListing
import com.knowledgeview.tablet.arabnews.models.repositories.VideoGalleryRepository
import javax.inject.Inject

/**
 * Created by naderbaltaji on 3/23/19
 */
class VideoGalleryViewModel @Inject constructor(videoGalleryListingRepository: VideoGalleryRepository) : ViewModel(){

    private var videoGalleryList: LiveData<VideoGalleryListing>? = null

    init {
        videoGalleryList = videoGalleryListingRepository.getPhotoGallery()
    }

    fun getVideoGallery() : LiveData<VideoGalleryListing> {
        return this.videoGalleryList!!
    }
}