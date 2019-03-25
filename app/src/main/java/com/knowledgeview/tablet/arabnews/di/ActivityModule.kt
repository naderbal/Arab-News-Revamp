package com.knowledgeview.tablet.arabnews.di

import com.knowledgeview.tablet.arabnews.view.*
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
    abstract fun contributeOpinionDetailsActivity(): OpinionDetailsPageActivity

    @ContributesAndroidInjector
    abstract fun contributeVideoDetailsActivity(): VideoDetailsActivity

    @ContributesAndroidInjector
    abstract fun contributeAuthorDetailsActivity(): AuthorDetailsActivity
}