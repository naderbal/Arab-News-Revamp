package com.knowledgeview.tablet.arabnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.knowledgeview.tablet.arabnews.models.data.ParentSection
import com.knowledgeview.tablet.arabnews.models.repositories.SectionListingRepository
import com.knowledgeview.tablet.arabnews.vo.Resource
import com.srpc.independantminds.model.local.Prefs
import javax.inject.Inject

class SectionListingViewModel @Inject constructor(private val sectionListingRepository: SectionListingRepository) : ViewModel(){
    private var sections: LiveData<Resource<List<ParentSection>>>? = null


    init {
        sections = sectionListingRepository.getSections()
    }

    fun getSections(): LiveData<Resource<List<ParentSection>>> {
        return this.sections!!
    }
}