package com.knowledgeview.tablet.arabnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.knowledgeview.tablet.arabnews.models.data.HomeListing
import com.knowledgeview.tablet.arabnews.models.repositories.AuthorOpinionsListingRepository
import javax.inject.Inject

class AuthorOpinionsListingViewModel @Inject constructor(private val homeListingRepository: AuthorOpinionsListingRepository) : ViewModel(){
    private var opinionsList: LiveData<HomeListing>? = null

    fun fetchAuthorOpinionsList(authorId: String) {
        opinionsList = homeListingRepository.getAuthorsOpinionsData(authorId)
    }

    fun getOpinionsList(): LiveData<HomeListing> {
        return this.opinionsList!!
    }
}