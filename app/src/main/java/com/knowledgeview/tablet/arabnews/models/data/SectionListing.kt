package com.knowledgeview.tablet.arabnews.models.data

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity
class SectionListing {

    @NonNull
    @PrimaryKey
    @SerializedName("entity_id")
    private var entityID: String = ""
    private var label: String? = null
    @SerializedName("picture_small")
    private var pictureSmall: List<String>? = null
    @SerializedName("sm_field_main_image")
    private var pictureLarge: List<String>? = null
    private var categoryID: Int? = null
    @SerializedName("sm_field_author")
    private var author: List<String>? = null
    @SerializedName("ds_created")
    private var date: Date? = null
    @SerializedName("has_faceimage")
    private var hasFace: Boolean? = false
    @SerializedName("sm_field_bullets")
    private var bullets: List<String>? = null

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


    fun getBullets(): List<String>? {
        return bullets
    }

    fun setBullets(bullets: List<String>?) {
        this.bullets = bullets
    }


    fun getFace(): Boolean? {
        return hasFace
    }

    fun setFace(hasFace: Boolean?) {
        this.hasFace = hasFace
    }


    fun getDate(): Date? {
        return date
    }

    fun setDate(date: Date?) {
        this.date = date
    }

    fun getPictureSmall(): List<String>? {
        return pictureSmall
    }

    fun setPictureSmall(images: List<String>?) {
        this.pictureSmall = images
    }

    fun getAuthor(): List<String>? {
        return author
    }

    fun setAuthor(author: List<String>?) {
        this.author = author
    }

    fun getPictureLarge(): List<String>? {
        return pictureLarge
    }

    fun setPictureLarge(images: List<String>?) {
        this.pictureLarge = images
    }


    fun getCategoryID(): Int? {
        return categoryID
    }

    fun setCategoryID(name: Int?) {
        this.categoryID = name
    }


}