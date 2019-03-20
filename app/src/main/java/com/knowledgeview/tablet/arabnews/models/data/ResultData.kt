package com.knowledgeview.tablet.arabnews.models.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ResultData {
    @PrimaryKey
    var type:String=""
    var data:String?=null
}