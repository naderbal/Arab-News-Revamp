package com.knowledgeview.tablet.arabnews.models.data

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
class ParentSection {
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    private var tid = ""
    private var vid = ""
    private var title:String?=null
    private var type:String?=null
    private var icon: Int?=null
    @SerializedName("sub")
    private var children: List<ParentSection> ?=null

    constructor(@NonNull name: String, icon: Int?, children: List<ParentSection>?) {
        this.tid = name
        this.icon=icon
        this.children=children
        this.title = name
    }


    constructor(){}

    @NonNull
    fun getTid(): String {
        return tid
    }

    fun setTid(@NonNull tid: String) {
        this.tid = tid
    }

    fun getVid(): String {
        return vid
    }

    fun setVid(vid: String) {
        this.vid = vid
    }

    fun getIcon(): Int? {
        return icon
    }

    fun setIcon(icon: Int?) {
        this.icon = icon
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(name: String?) {
        this.title = name
    }

    fun getType(): String? {
        return type
    }

    fun setType(type: String?) {
        this.type = type
    }

    fun getChildren(): List<ParentSection>? {
        return children
    }

    fun setChildren(children: List<ParentSection>?) {
        this.children = children
    }
}