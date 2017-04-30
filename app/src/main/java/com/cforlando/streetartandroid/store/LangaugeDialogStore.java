package com.cforlando.streetartandroid.store;

import com.cforlando.streetartandroid.action.Actions;
import com.cforlando.streetartandroid.util.Constants;
import com.hardsoftstudio.rxflux.action.RxAction;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;

import javax.inject.Inject;

/**
 * Created by Nigel.Brown on 3/29/2017.
 */
public class LangaugeDialogStore extends RxStore {
    @Inject
    public LangaugeDialogStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case Actions.SHOW_LANGUAGE_DIALOG:
                break;
        }
        postChange(new RxStoreChange(Constants.LANGUAGE_DIALOG_STORE_ID, action));
    }
}
