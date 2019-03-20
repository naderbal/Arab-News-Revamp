package com.knowledgeview.tablet.arabnews.models.data

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity
class ReadingList {
    @PrimaryKey
    @NonNull
    private var entityID: String = ""
    private var type: String? = null
    private var images: List<String>? = null
    private var label: String? = null
    private var author: String? = null
    private var date:Date?=null


    constructor() {}

    constructor(sectionListing: SectionListing) {
        this.entityID = sectionListing.getEntityID()
        this.type = "section"
        this.images = sectionListing.getPictureLarge()
        this.label = sectionListing.getLabel()
        if (!sectionListing.getAuthor().isNullOrEmpty())
            this.author = sectionListing.getAuthor()!![0]
        this.date=sectionListing.getDate()
    }

    constructor(homeData: HomeData,type:String) {
        this.entityID = homeData.getEntityID()
        this.type = type
        this.images = homeData.getPictureLarge()
        this.label = homeData.getLabel()
        if (!homeData.getAuthor().isNullOrEmpty())
            this.author = homeData.getAuthor()!![0]
        this.date=homeData.getDate()
    }

    constructor(video:Video) {
        this.entityID = video.getEntityID()
        this.type = "section"
        this.images = video.getPictureLarge()
        this.label = video.getLabel()
        if (!video.getAuthor().isNullOrEmpty())
            this.author = video.getAuthor()!![0]
        this.date=video.getDate()
    }

    fun getDate(): Date? {
        return date
    }

    fun setDate(date: Date?) {
        this.date = date
    }

    @NonNull
    fun getEntityID(): String {
        return entityID
    }

    fun setEntityID(@NonNull tid: String) {
        this.entityID = tid
    }

    fun getLabel(): String? {
        return label
    }

    fun setLabel(name: String?) {
        this.label = name
    }

    fun getAuthor(): String? {
        return author
    }

    fun setAuthor(name: String?) {
        this.author = name
    }

    fun getImages(): List<String>? {
        return images
    }

    fun setImages(images: List<String>?) {
        this.images = images
    }

    fun getType(): String? {
        return type
    }

    fun setType(type: String?) {
        this.type = type
    }

}