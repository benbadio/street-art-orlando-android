package com.cforlando.streetartandroid.view.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.cforlando.streetartandroid.dagger.component.AppComponent;
import com.cforlando.streetartandroid.view.activity.MainActivity;

/**
 * Created by benbadio on 4/4/17.
 */

public class BaseFragment extends Fragment {
    public void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = getActivity().getCurrentFocus();
        if (v == null) {
            return;
        }
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    protected AppComponent getComponent() {
        return ((MainActivity) getActivity()).getComponent();
    }
}
