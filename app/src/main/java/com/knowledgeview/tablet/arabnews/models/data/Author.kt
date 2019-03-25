package com.knowledgeview.tablet.arabnews.models.data

import androidx.room.Entity
import com.google.gson.annotations.SerializedName


@Entity
class Author {
    /*
    * "description":"<p>\r\n\tEyad Abu Shakra is managing editor of Asharq Al-Awsat.</p>\r\n\r\n<p>\r\n\tTwitter: @eyad1949</p>\r\n",
                  "name":"Eyad Abu Shakra",
                  "tid":"5771",
                  "field_author_image":"http://ndemo.arabnews.com/sites/default/files/styles/large/public/2018/08/17/1212696-1534526700-20.png?itok=z9E5ijD-",
                  "metatags":null
    * */

    var name:String?=null
    var tid:String=""
    @SerializedName("field_author_image")
    var authorImage:String?=null
    @SerializedName("description")
    var description: String? = null
}