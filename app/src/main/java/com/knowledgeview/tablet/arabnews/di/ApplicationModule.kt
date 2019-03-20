package com.knowledgeview.tablet.arabnews.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.knowledgeview.tablet.arabnews.models.local.ArabNewsDatabase
import com.knowledgeview.tablet.arabnews.models.local.DaoAccess
import com.knowledgeview.tablet.arabnews.models.networking.apis.*
import com.knowledgeview.tablet.arabnews.utils.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class ApplicationModule {

    @Provides
    @Singleton
    fun providesContext(app: Application): Context = app

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("http://ndemo.arabnews.com/jsonfeed/api/v2/")
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitSections(retrofit: Retrofit): GetParentSection {
        return retrofit.create(GetParentSection::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofitViews(retrofit: Retrofit): GetWebViews {
        return retrofit.create(GetWebViews::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofitGetNodeDetails(retrofit: Retrofit): GetNodeDetails {
        return retrofit.create(GetNodeDetails::class.java)
    }


    @Singleton
    @Provides
    fun provideRetrofitHomeData(retrofit: Retrofit): PostHomeData {
        return retrofit.create(PostHomeData::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofitNews(retrofit: Retrofit): GetNews {
        return retrofit.create(GetNews::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(context: Context): ArabNewsDatabase {
        return Room
                .databaseBuilder(context, ArabNewsDatabase::class.java, "ArabNewsDatabase.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }

    @Singleton
    @Provides
    fun provideDao(db: ArabNewsDatabase): DaoAccess {
        return db.daoAccess()
    }
}