package com.knowledgeview.tablet.arabnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.knowledgeview.tablet.arabnews.models.data.Node
import com.knowledgeview.tablet.arabnews.models.repositories.NodeDetailsRepository
import com.knowledgeview.tablet.arabnews.vo.Resource
import javax.inject.Inject

class NodeViewModel @Inject constructor(private val nodeDetailsRepository: NodeDetailsRepository) : ViewModel() {
    private var nodeDetails: LiveData<Resource<List<Node>>>? = null


    fun fetchDetails(entityID: String){
        nodeDetails = nodeDetailsRepository.getNodeDetails(entityID)
    }

    fun getNodeDetails(): LiveData<Resource<List<Node>>> {
        return this.nodeDetails!!
    }
}