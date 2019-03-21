package com.knowledgeview.tablet.arabnews.di

import com.knowledgeview.tablet.arabnews.view.MainActivity
import com.knowledgeview.tablet.arabnews.view.NodeDetailsActivity
import com.knowledgeview.tablet.arabnews.view.OpinionDetailsPage
import com.knowledgeview.tablet.arabnews.view.TutorialActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeTutorialActivity(): TutorialActivity

    @ContributesAndroidInjector
    abstract fun contributeNodeDetailsActivity(): NodeDetailsActivity

    @ContributesAndroidInjector
    abstract fun contributeOpinionDetailsActivity(): OpinionDetailsPage


}