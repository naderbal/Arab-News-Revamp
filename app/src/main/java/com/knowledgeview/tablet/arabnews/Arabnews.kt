package com.knowledgeview.tablet.arabnews

import android.app.Activity
import android.app.Application
import com.knowledgeview.tablet.arabnews.di.AppInjector
import com.knowledgeview.tablet.arabnews.view.services.NotificationOpened
import com.onesignal.OneSignal
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by Miriana on 3/22/2018.
 */
class Arabnews : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = dispatchingAndroidInjector
    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(NotificationOpened(applicationContext))
                .init()

    }
}