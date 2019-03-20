package com.knowledgeview.tablet.arabnews.view.services

import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.models.networking.ServiceFactory
import com.knowledgeview.tablet.arabnews.models.networking.apis.PostToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
import org.json.JSONObject


/**
 * Created by Miriana on 2/13/2018.
 */
class FirebaseMessageService : FirebaseMessagingService() {
    override fun onNewToken(refreshedToken: String?) {
        super.onNewToken(refreshedToken)
        if (refreshedToken != null) {
            val postToken = ServiceFactory.createRetrofitService(PostToken::class.java, getString(R.string.notification_url))
            postToken.sendToken(refreshedToken, "7501a2f7-fe3d-40fb-808b-3db2925d7a8e", "1", "1")
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ _ ->
                    },
                            { error ->
                                error.printStackTrace()
                            })
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        val broadCastIntent = Intent()
        val map = remoteMessage!!.data
        val title = map["title"]
        val message = map["alert"]
        val json = map["custom"]
        broadCastIntent.putExtra("title", title)
        broadCastIntent.putExtra("message", message)
        if (json != null) {
            try {
                val jsonObj = JSONObject(json)
                if (jsonObj.has("a")) {
                    val jsonObject = jsonObj.getJSONObject("a")
                    if (jsonObject.has("type"))
                        broadCastIntent.putExtra("type",jsonObject.getString("type"))
                    if (jsonObject.has("articleId"))
                        broadCastIntent.putExtra("entityID", jsonObject.getString("articleId"))
                }
            } catch (e: JSONException) {
            }
        }
        broadCastIntent.action = "in.app.notifications.arabnews"
        sendBroadcast(broadCastIntent)
    }
}