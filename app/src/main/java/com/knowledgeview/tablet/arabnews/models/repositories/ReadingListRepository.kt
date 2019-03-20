package com.knowledgeview.tablet.arabnews.models.repositories

import androidx.lifecycle.LiveData
import com.knowledgeview.tablet.arabnews.models.data.ReadingList
import com.knowledgeview.tablet.arabnews.models.local.DaoAccess
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ReadingListRepository @Inject constructor(
        private val newsDoa: DaoAccess
) {


    fun getReadingList(): LiveData<List<ReadingList>> {
       return newsDoa.allReadingList
    }
}