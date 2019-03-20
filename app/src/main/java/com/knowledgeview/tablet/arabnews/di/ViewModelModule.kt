package com.knowledgeview.tablet.arabnews.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.knowledgeview.tablet.arabnews.viewmodel.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SectionListingViewModel::class)
    abstract fun bindSectionsViewModel(tabViewModel: SectionListingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReadingListViewModel::class)
    abstract fun bindReadingListViewModel(tabViewModel: ReadingListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeListingViewModel::class)
    abstract fun bindHomeListViewModel(tabViewModel: HomeListingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SharedViewModel::class)
    abstract fun bindSharedViewModel(sharedViewModel: SharedViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(NodeViewModel::class)
    abstract fun bindNodeViewModel(sharedViewModel: NodeViewModel): ViewModel

}




