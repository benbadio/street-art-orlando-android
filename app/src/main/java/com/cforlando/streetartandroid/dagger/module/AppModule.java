package com.cforlando.streetartandroid.dagger.module;

import com.cforlando.streetartandroid.App;
import com.hardsoftstudio.rxflux.RxFlux;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.util.SubscriptionManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Nigel.Brown on 3/30/2017.
 */
@Module
public class AppModule {
    private App mApp;

    public AppModule(App app) {
        this.mApp = app;
    }

    @Singleton
    @Provides
    public App providesApplication() {return this.mApp;}

    @Singleton
    @Provides
    public RxFlux providesRxFLux(App app) {return RxFlux.init(app);}

    @Singleton
    @Provides
    public SubscriptionManager providesSubscriptionManager(RxFlux rxFlux) {
        return rxFlux.getSubscriptionManager();
    }

    @Singleton
    @Provides
    public Dispatcher providesDispatcher(RxFlux rxFlux) {
        return rxFlux.getDispatcher();
    }
}
