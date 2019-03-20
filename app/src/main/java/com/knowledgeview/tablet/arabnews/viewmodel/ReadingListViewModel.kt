package com.knowledgeview.tablet.arabnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.knowledgeview.tablet.arabnews.models.data.ReadingList
import com.knowledgeview.tablet.arabnews.models.repositories.ReadingListRepository
import javax.inject.Inject

class ReadingListViewModel @Inject constructor(readingListRepository: ReadingListRepository) : ViewModel(){
    private var readingList: LiveData<List<ReadingList>>? = null


    init {
        readingList = readingListRepository.getReadingList()
    }

    fun getReadingList(): LiveData<List<ReadingList>> {
        return this.readingList!!
    }
}