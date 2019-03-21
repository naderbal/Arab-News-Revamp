package com.knowledgeview.tablet.arabnews.models.repositories

import androidx.lifecycle.LiveData
import com.knowledgeview.tablet.arabnews.AppExecutors
import com.knowledgeview.tablet.arabnews.models.data.Node
import com.knowledgeview.tablet.arabnews.models.data.NodeDetailsMainResult
import com.knowledgeview.tablet.arabnews.models.data.ParentSection
import com.knowledgeview.tablet.arabnews.models.data.SectionResult
import com.knowledgeview.tablet.arabnews.models.local.DaoAccess
import com.knowledgeview.tablet.arabnews.models.networking.apis.GetNodeDetails
import com.knowledgeview.tablet.arabnews.models.networking.apis.GetParentSection
import com.knowledgeview.tablet.arabnews.models.networking.apis.PostOpinionDetails
import com.knowledgeview.tablet.arabnews.vo.NetworkBoundResource
import com.knowledgeview.tablet.arabnews.vo.Resource
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NodeDetailsRepository @Inject constructor(
        private val getNodeDetails: GetNodeDetails,
        private val newsDoa: DaoAccess,
        private val postOpinionDetails: PostOpinionDetails,
        private val appExecutors: AppExecutors
) {


    fun getNodeDetails(nodeID: String): LiveData<Resource<List<Node>>> {
        return object : NetworkBoundResource<List<Node>, NodeDetailsMainResult>(appExecutors) {
            override fun saveCallResult(item: NodeDetailsMainResult) {
                if (item.data != null) {
                    if (!item.data!!.nodeDetails.isNullOrEmpty()) {
                        newsDoa.insertNodes(item.data!!.nodeDetails)
                    }
                }
            }

            override fun shouldFetch(data: List<Node>?) = data.isNullOrEmpty()

            override fun loadFromDb() = newsDoa.getNodes(nodeID)

            override fun createCall() = getNodeDetails.getNodeDetails(nodeID)
        }.asLiveData()
    }

    fun getOpinionDetails(nodeID: String, authorID: String): LiveData<Resource<List<Node>>> {
        return object : NetworkBoundResource<List<Node>, NodeDetailsMainResult>(appExecutors) {
            override fun saveCallResult(item: NodeDetailsMainResult) {
                if (item.data != null) {
                    if (!item.data!!.nodeDetails.isNullOrEmpty()) {
                        item.data!!.nodeDetails!![0].opinionList = item.data!!.opinionArticlesList
                        newsDoa.insertNodes(item.data!!.nodeDetails)
                    }
                }
            }

            override fun shouldFetch(data: List<Node>?) = data.isNullOrEmpty()

            override fun loadFromDb() = newsDoa.getNodes(nodeID)

            override fun createCall() = postOpinionDetails.fetchOpinionData(authorID, nodeID)
        }.asLiveData()
    }
}