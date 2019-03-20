package com.knowledgeview.tablet.arabnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.knowledgeview.tablet.arabnews.models.data.HomeListing
import com.knowledgeview.tablet.arabnews.models.repositories.HomeListingRepository
import javax.inject.Inject

class HomeListingViewModel @Inject constructor(homeListingRepository: HomeListingRepository) : ViewModel(){
    private var homeList: LiveData<HomeListing>? = null


    init {
        homeList = homeListingRepository.getHomeScreenData()
    }

    fun getHomeList(): LiveData<HomeListing> {
        return this.homeList!!
    }
}