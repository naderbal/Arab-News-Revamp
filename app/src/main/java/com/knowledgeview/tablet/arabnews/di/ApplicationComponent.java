package com.knowledgeview.tablet.arabnews.di;

import android.app.Application;

import com.knowledgeview.tablet.arabnews.Arabnews;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {ApplicationModule.class, AndroidInjectionModule.class,AndroidSupportInjectionModule.class,
        ActivityModule.class,ViewModelModule.class,FragmentModule.class})
public interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        ApplicationComponent build();
    }

    void inject(Arabnews application);
}
