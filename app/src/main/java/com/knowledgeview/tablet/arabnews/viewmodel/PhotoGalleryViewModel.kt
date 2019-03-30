package com.knowledgeview.tablet.arabnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.knowledgeview.tablet.arabnews.models.data.PhotoGalleryListing
import com.knowledgeview.tablet.arabnews.models.repositories.PhotoGalleryRepository
import javax.inject.Inject

/**
 * Created by naderbaltaji on 3/23/19
 */
class PhotoGalleryViewModel @Inject constructor(photoGalleryListingRepository: PhotoGalleryRepository) : ViewModel(){

    private var photoGalleryList: LiveData<PhotoGalleryListing>? = null

    init {
        photoGalleryList = photoGalleryListingRepository.getPhotoGallery()
    }

    fun getPhotoGallery() : LiveData<PhotoGalleryListing> {
        return this.photoGalleryList!!
    }
}