package com.cforlando.streetartandroid.store;

import com.cforlando.streetartandroid.action.Actions;
import com.cforlando.streetartandroid.action.Keys;
import com.cforlando.streetartandroid.util.Constants;
import com.hardsoftstudio.rxflux.action.RxAction;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;

import javax.inject.Inject;

/**
 * Created by Nigel.Brown on 3/29/2017.
 */
public class OrganizationsStore extends RxStore implements OrganizationsStoreInterface {
    private String mOrganizationId;

    @Inject
    public OrganizationsStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case Actions.SHOW_SPLASH:
                this.mOrganizationId = action.get(Keys.ID);
                break;
        }
        postChange(new RxStoreChange(Constants.ORGANIZATION_STORE_ID, action));
    }

    @Override
    public String getOrganizationId() {
        return mOrganizationId;
    }
}
