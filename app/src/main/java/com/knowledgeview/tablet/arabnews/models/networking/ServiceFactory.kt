package com.knowledgeview.tablet.arabnews.models.networking

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by Miriana on 1/24/2018.
 */
class ServiceFactory {

    /**
     * Creates a retrofit service from an arbitrary class (clazz)
     * @param clazz Java interface of the retrofit service
     * @param endPoint REST endpoint url
     * @return retrofit service with defined endpoint
     */
    companion object {
        //        2018-01-23T20:52:31Z
        var gson = GsonBuilder()
                .setDateFormat("yyyy-M-dd'T'HH:mm:ss'Z'").create()

        fun <T> createRetrofitService(clazz: Class<T>, baseURL: String): T {
            val restAdapter = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(baseURL)
                    .build()
            return restAdapter.create(clazz)
        }
    }
}