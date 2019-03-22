package com.knowledgeview.tablet.arabnews.models.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.knowledgeview.tablet.arabnews.models.data.Author
import com.knowledgeview.tablet.arabnews.models.data.HomeData
import com.knowledgeview.tablet.arabnews.models.data.ParentSection
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by Miriana on 1/29/2018.
 */
class Converters {
    @TypeConverter
    fun fromString(value: String?): MutableList<String>? {
        val listType = object : TypeToken<MutableList<String>>() {

        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromParentSection(value: String?): MutableList<ParentSection>? {
        return if(value!=null) {
            val listType = object : TypeToken<MutableList<ParentSection>>() {

            }.type
            Gson().fromJson(value, listType)
        }else null
    }

    @TypeConverter
    fun toParentSection(list: MutableList<ParentSection>?): String? {
        if(list!=null) {
            val gson = Gson()
            return gson.toJson(list)
        }else return null
    }

    @TypeConverter
    fun fromArrayList(list: MutableList<String>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun toHomeDataList(list: MutableList<HomeData>?): String? {
        if(list!=null) {
            val gson = Gson()
            return gson.toJson(list)
        }else return null
    }

    @TypeConverter
    fun fromHomeDateArrayList(value: String?): MutableList<HomeData>? {
        return if(value!=null) {
            val listType = object : TypeToken<MutableList<HomeData>>() {

            }.type
            Gson().fromJson(value, listType)
        }else null
    }

    @TypeConverter
    fun fromStringInt(value: String?): MutableList<Int>? {
        val listType = object : TypeToken<MutableList<Int>>() {

        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayListInt(list: MutableList<Int>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return if (date != null) {
            (date.time)
        } else {
            (Date().time)
        }
    }

    @TypeConverter
    fun fromAuthor(value: String?): Author? {
        return if(value!=null) {
            val listType = object : TypeToken<Author>() {

            }.type
            Gson().fromJson(value, listType)
        }else null
    }

    @TypeConverter
    fun toAuthor(list: Author?): String? {
        return if(list!=null) {
            val gson = Gson()
            gson.toJson(list)
        }else null
    }
}