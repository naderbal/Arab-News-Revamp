package com.knowledgeview.tablet.arabnews.models.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.knowledgeview.tablet.arabnews.models.data.ParentSection
import java.util.*


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
        if(value!=null) {
            val listType = object : TypeToken<MutableList<ParentSection>>() {

            }.type
            return Gson().fromJson(value, listType)
        }else return null
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

  /*  @TypeConverter
    fun fromSection(value: String?): MutableList<Related>? {
        val listType = object : TypeToken<MutableList<Related>>() {

        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: MutableList<Related>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }*/

 /*   @TypeConverter
    fun fromMainSection(value: String): MutableList<Children> {
        val listType = object : TypeToken<MutableList<Children>>() {

        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromMainList(list: MutableList<Children>): String {
        val gson = Gson()
        return gson.toJson(list)
    }*/

    /*

     @TypeConverter
     fun fromParentSection(value: String): MutableList<ParentSection> {
         val listType = object : TypeToken<MutableList<ParentSection>>() {

         }.type
         return Gson().fromJson(value, listType)
     }

     @TypeConverter
     fun toParentSection(list: MutableList<ParentSection>): String {
         val gson = Gson()
         return gson.toJson(list)
     }*/
}