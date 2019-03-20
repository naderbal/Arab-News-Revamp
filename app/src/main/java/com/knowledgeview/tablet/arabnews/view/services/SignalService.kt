package com.knowledgeview.tablet.arabnews.view.services

import android.util.Log
import androidx.core.app.NotificationCompat
import com.knowledgeview.tablet.arabnews.R
import com.onesignal.NotificationExtenderService
import com.onesignal.OSNotificationReceivedResult
import com.srpc.independantminds.model.local.Prefs

class SignalService: NotificationExtenderService() {
    override fun onNotificationProcessing(notification: OSNotificationReceivedResult?): Boolean {
        if(Prefs.getInstance(applicationContext).getNotification()) {
            val overrideSettings = NotificationExtenderService.OverrideSettings()
            overrideSettings.extender = NotificationCompat.Extender { builder -> builder.setSmallIcon(R.drawable.logo_header) }
            val displayedResult = displayNotification(overrideSettings)
            Log.d("OneSignalExample", "Notification displayed with id: " + displayedResult.androidNotificationId)
        }
        return true
    }
}