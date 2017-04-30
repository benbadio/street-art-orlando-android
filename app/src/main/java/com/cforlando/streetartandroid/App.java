package com.cforlando.streetartandroid;

import android.app.Application;

import com.cforlando.streetartandroid.dagger.component.AppComponent;
import com.cforlando.streetartandroid.dagger.component.DaggerAppComponent;
import com.cforlando.streetartandroid.dagger.module.ActionsCreatorModule;
import com.cforlando.streetartandroid.dagger.module.AppModule;
import com.cforlando.streetartandroid.dagger.module.StoreModule;

/**
 * Created by benbadio on 4/3/17.
 */

public class App extends Application {
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setComponent(createComponent());
    }

    protected AppComponent createComponent() {
        return DaggerAppComponent.builder().appModule(new AppModule(this))
                .actionsCreatorModule(new ActionsCreatorModule())
                .storeModule(new StoreModule()).build();
    }

    public AppComponent getComponent() {
        return mAppComponent;
    }

    public void setComponent(AppComponent component) {
        this.mAppComponent = component;
    }
}