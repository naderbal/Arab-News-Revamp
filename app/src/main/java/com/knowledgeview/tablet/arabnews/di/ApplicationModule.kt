package com.knowledgeview.tablet.arabnews.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.knowledgeview.tablet.arabnews.BuildConfig
import com.knowledgeview.tablet.arabnews.models.local.ArabNewsDatabase
import com.knowledgeview.tablet.arabnews.models.local.DaoAccess
import com.knowledgeview.tablet.arabnews.models.networking.apis.*
import com.knowledgeview.tablet.arabnews.utils.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class ApplicationModule {

    @Provides
    @Singleton
    fun providesContext(app: Application): Context = app

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val builder = OkHttpClient().newBuilder()
        builder.readTimeout(60, TimeUnit.SECONDS)
        builder.connectTimeout(60, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
            builder.addInterceptor(interceptor)
        }

        return Retrofit.Builder().baseUrl("http://www.arabnews.com/jsonfeed/api/v2/")
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
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
    fun provideRetrofitOpinionDetails(retrofit: Retrofit): PostOpinionDetails {
        return retrofit.create(PostOpinionDetails::class.java)
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