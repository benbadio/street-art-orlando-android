package com.cforlando.streetartandroid.dagger.component;

import com.cforlando.streetartandroid.dagger.module.ActionsCreatorModule;
import com.cforlando.streetartandroid.dagger.module.AppModule;
import com.cforlando.streetartandroid.dagger.module.StoreModule;
import com.cforlando.streetartandroid.view.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Nigel.Brown on 3/30/2017.
 */
@Singleton
@Component(modules = {AppModule.class, ActionsCreatorModule.class, StoreModule.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);
}
