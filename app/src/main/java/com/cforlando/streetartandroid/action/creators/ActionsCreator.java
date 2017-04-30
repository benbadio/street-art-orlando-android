package com.cforlando.streetartandroid.action.creators;

import com.cforlando.streetartandroid.action.Actions;
import com.cforlando.streetartandroid.action.Keys;
import com.hardsoftstudio.rxflux.action.RxAction;
import com.hardsoftstudio.rxflux.action.RxActionCreator;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.util.SubscriptionManager;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Nigel.Brown on 3/29/2017.
 */
public class ActionsCreator extends RxActionCreator implements Actions {
    @Inject
    public ActionsCreator(Dispatcher dispatcher, SubscriptionManager manager) {
        super(dispatcher, manager);
    }

    @Override
    public void showOrganizationSplash(String orgId) {
        final RxAction action = newRxAction(SHOW_SPLASH);
        addRxAction(action, Observable.just(orgId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(id -> postRxAction(
                        newRxAction(SHOW_SPLASH, Keys.ID, id)),
                        throwable -> postError(action, throwable)));
    }

    @Override
    public void showLangaugeDialog() {
        final RxAction action = newRxAction(SHOW_LANGUAGE_DIALOG);
        addRxAction(action, Observable.empty().observeOn(AndroidSchedulers.mainThread()).subscribe(
                s -> postRxAction(newRxAction(SHOW_LANGUAGE_DIALOG)),
                throwable -> postError(action, throwable)));
    }
}
