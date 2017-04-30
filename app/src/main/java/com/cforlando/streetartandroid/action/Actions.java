package com.cforlando.streetartandroid.action;

/**
 * Created by Nigel.Brown on 3/29/2017.
 */
public interface Actions {
    String SHOW_SPLASH = "show_splash";
    String SHOW_LANGUAGE_DIALOG = "show_language_dialog";
    void showOrganizationSplash(String orgId);
    void showLangaugeDialog();
}
