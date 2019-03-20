package com.knowledgeview.tablet.arabnews.view.services

import android.content.Context
import android.content.Intent
import com.knowledgeview.tablet.arabnews.view.ArticleDetailsActivity
import com.knowledgeview.tablet.arabnews.view.MainActivity
import com.onesignal.OSNotificationOpenResult
import com.onesignal.OneSignal

/**
 * Created by Miriana on 3/13/2018.
 */
class NotificationOpened(context: Context) :  OneSignal.NotificationOpenedHandler {
    private var context: Context? = null

    init {
        this.context = context
    }

    override fun notificationOpened(result: OSNotificationOpenResult?) {
        if (result != null) {
            val objectJSON = result.notification.payload.additionalData
            if (objectJSON != null) {
                val intent = Intent()
                intent.setClass(context!!, ArticleDetailsActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                if (objectJSON.has("articleId")) {
                    intent.putExtra("entityID", objectJSON.getString("articleId"))
                    context!!.startActivity(intent)
                } else goToHomePage()
            } else goToHomePage()
        } else {
            goToHomePage()
        }
    }

    private fun goToHomePage() {
        val intent = Intent()
        intent.setClass(context!!, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_NEW_TASK
        context!!.startActivity(intent)
    }
}