package com.knowledgeview.tablet.arabnews

import android.app.Activity
import android.app.Application
import com.knowledgeview.tablet.arabnews.di.AppInjector
import com.knowledgeview.tablet.arabnews.view.services.NotificationOpened
import com.onesignal.OneSignal
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject
import com.instabug.library.invocation.InstabugInvocationEvent
import com.instabug.library.Instabug



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

        Instabug.Builder(this, "50a54cb0f79e53fd2f7d9d1571757786")
                .setInvocationEvents(InstabugInvocationEvent.SHAKE, InstabugInvocationEvent.SCREENSHOT)
                .build()
    }
}