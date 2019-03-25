package com.knowledgeview.tablet.arabnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.knowledgeview.tablet.arabnews.models.data.Author
import com.knowledgeview.tablet.arabnews.models.data.AuthorListing
import com.knowledgeview.tablet.arabnews.models.data.HomeListing
import com.knowledgeview.tablet.arabnews.models.repositories.OpinionsListingRepository
import javax.inject.Inject

class OpinionsListingViewModel @Inject constructor(homeListingRepository: OpinionsListingRepository) : ViewModel(){
    private var opinionsList: LiveData<HomeListing>? = null
    private var authorsList: LiveData<List<Author>>? = null


    init {
        opinionsList = homeListingRepository.getOpinionsScreenData()
        authorsList = homeListingRepository.getAuthorsListData()
    }

    fun getOpinionsList(): LiveData<HomeListing> {
        return this.opinionsList!!
    }

    fun getAuthorsList(): LiveData<List<Author>> {
        return this.authorsList!!
    }
}