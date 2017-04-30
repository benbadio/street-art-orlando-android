package com.cforlando.streetartandroid.dagger.module;

import com.cforlando.streetartandroid.action.creators.ActionsCreator;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.util.SubscriptionManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Nigel.Brown on 3/30/2017.
 */
@Module
public class ActionsCreatorModule {
    @Singleton
    @Provides
    public ActionsCreator providesActionCreator(Dispatcher dispatcher, SubscriptionManager manager) {
        return new ActionsCreator(dispatcher, manager);
    }
}
