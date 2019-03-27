package com.knowledgeview.tablet.arabnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.knowledgeview.tablet.arabnews.models.data.Node
import com.knowledgeview.tablet.arabnews.models.repositories.OpinionDetailsRepository
import com.knowledgeview.tablet.arabnews.vo.Resource
import javax.inject.Inject

class OpinionDetailsViewModel @Inject constructor(private val opinionDetailsRepository: OpinionDetailsRepository) : ViewModel() {
    private var opinionDetails: LiveData<Resource<Node>>? = null

    fun fetchOpinionDetails(entityID: String,authorID:String){
        opinionDetails = opinionDetailsRepository.getOpinionDetails(entityID,authorID)
    }

    fun getOpinionDetails(): LiveData<Resource<Node>> {
        return this.opinionDetails!!
    }
}