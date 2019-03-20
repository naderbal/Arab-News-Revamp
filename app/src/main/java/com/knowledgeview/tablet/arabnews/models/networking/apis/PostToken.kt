package com.knowledgeview.tablet.arabnews.models.networking.apis

import com.knowledgeview.tablet.arabnews.models.data.Result
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Miriana on 2/13/2018.
 */
interface PostToken {
    @FormUrlEncoded
    @POST("notify")
    fun sendToken(@Field("device_id") token: String, @Field("app_id") appID: String,
                  @Field("device_type") deviceType: String, @Field("demo") demo: String)
            : Observable<Result>
}