package com.knowledgeview.tablet.arabnews.di

import com.knowledgeview.tablet.arabnews.view.fragments.*
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeScreenListingFragmentModule(): ScreenListingFragment

    @ContributesAndroidInjector
    abstract fun contributeReadingListFragmentModule(): ReadingListFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragmentModule(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeVideoFragmentModule(): VideosFragment

    @ContributesAndroidInjector
    abstract fun contributeWelcomeFragmentModule(): WelcomeFragmentOne

}