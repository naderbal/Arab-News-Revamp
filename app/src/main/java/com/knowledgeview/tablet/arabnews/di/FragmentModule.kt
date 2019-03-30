package com.knowledgeview.tablet.arabnews.di

import com.knowledgeview.tablet.arabnews.view.fragments.*
import com.knowledgeview.tablet.arabnews.view.fragments.face.FaceGalleryFragment
import com.knowledgeview.tablet.arabnews.view.fragments.opinions.OpinionsListFragment
import com.knowledgeview.tablet.arabnews.view.fragments.photosGallery.PhotoGalleryFragment
import com.knowledgeview.tablet.arabnews.view.fragments.videoGallery.VideoGalleryFragment
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

    @ContributesAndroidInjector
    abstract fun contributeOpinionFragmentModule(): OpinionsListFragment

    @ContributesAndroidInjector
    abstract fun contributePhotoGalleryFragmentModule(): PhotoGalleryFragment

    @ContributesAndroidInjector
    abstract fun contributeVideoGalleryFragmentModule(): VideoGalleryFragment

    @ContributesAndroidInjector
    abstract fun contributeFaceGalleryFragmentModule(): FaceGalleryFragment
}