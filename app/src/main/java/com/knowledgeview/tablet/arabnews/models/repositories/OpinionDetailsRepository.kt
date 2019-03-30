package com.knowledgeview.tablet.arabnews.models.repositories

import androidx.lifecycle.LiveData
import com.knowledgeview.tablet.arabnews.AppExecutors
import com.knowledgeview.tablet.arabnews.models.data.*
import com.knowledgeview.tablet.arabnews.models.local.DaoAccess
import com.knowledgeview.tablet.arabnews.models.networking.apis.GetNodeDetails
import com.knowledgeview.tablet.arabnews.models.networking.apis.GetParentSection
import com.knowledgeview.tablet.arabnews.models.networking.apis.PostOpinionDetails
import com.knowledgeview.tablet.arabnews.vo.NetworkBoundResource
import com.knowledgeview.tablet.arabnews.vo.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OpinionDetailsRepository @Inject constructor(
        private val newsDoa: DaoAccess,
        private val postOpinionDetails: PostOpinionDetails,
        private val appExecutors: AppExecutors
) {

    fun getOpinionDetails(nodeID: String, authorID: String): LiveData<Resource<Node>> {
        return object : NetworkBoundResource<Node, OpinionDetailsMainResult>(appExecutors) {
            override fun saveCallResult(item: OpinionDetailsMainResult) {
                if (item.data != null) {
                    if (item.data!!.nodeDetails != null) {
                        //item.data!!.nodeDetails!!.nodes[0] = item.data!!.opinionArticlesList
                        newsDoa.insertNode(item.data!!.nodeDetails!!.nodes!![0])
                    }
                }
            }

            override fun shouldFetch(data: Node?) = data == null

            override fun loadFromDb() = newsDoa.getNode(nodeID)

            override fun createCall() = postOpinionDetails.fetchOpinionData(authorID, nodeID)
        }.asLiveData()
    }
}