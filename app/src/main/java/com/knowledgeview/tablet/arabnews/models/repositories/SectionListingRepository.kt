package com.knowledgeview.tablet.arabnews.models.repositories

import androidx.lifecycle.LiveData
import com.knowledgeview.tablet.arabnews.AppExecutors
import com.knowledgeview.tablet.arabnews.models.data.Node
import com.knowledgeview.tablet.arabnews.models.data.ParentSection
import com.knowledgeview.tablet.arabnews.models.data.SectionResult
import com.knowledgeview.tablet.arabnews.models.local.DaoAccess
import com.knowledgeview.tablet.arabnews.models.networking.apis.GetParentSection
import com.knowledgeview.tablet.arabnews.vo.NetworkBoundResource
import com.knowledgeview.tablet.arabnews.vo.Resource
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SectionListingRepository @Inject constructor(
        private val getParentSection: GetParentSection,
        private val newsDoa: DaoAccess,
        private val appExecutors: AppExecutors
) {


    fun getSections(): LiveData<Resource<List<ParentSection>>> {
        return object : NetworkBoundResource<List<ParentSection>, SectionResult>(appExecutors) {
            override fun saveCallResult(item: SectionResult) {
                if (!item.menu.isNullOrEmpty())
                    newsDoa.insertTabs(item.menu)
            }

            override fun shouldFetch(data: List<ParentSection>?) = true

            override fun loadFromDb() = newsDoa.allSections

            override fun createCall() = getParentSection.getSectionsResult()
        }.asLiveData()
    }
}