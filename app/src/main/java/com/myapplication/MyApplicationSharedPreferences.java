package com.myapplication;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by hedi on 11/27/17.
 */

public class MyApplicationSharedPreferences {

    public static final String SERVER_URL = "SERVER_URL";
    public static final String NETWORK_STATUS_CHANGES = "NETWORK_STATUS_CHANGES";
    public static final String IS_ALARM_MANAGER_SET = "IS_ALARM_MANAGER_SET";

    public String getServerURL() {
        SharedPreferences sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        String serverURL = sharedPreferencesEditor.getString(SERVER_URL, MyApplication.getAppContext().getResources().getString(R.string.main_screen_server_name_value_edit_text));
        return serverURL;
    }

    public void setServerURL(String serverURL) {
        SharedPreferences.Editor sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext()).edit();
        sharedPreferencesEditor.putString(SERVER_URL, serverURL);
        sharedPreferencesEditor.apply();
    }

    public List<String> getAllNetworkStatusChanges() {
        SharedPreferences sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        String serverURL = sharedPreferencesEditor.getString(NETWORK_STATUS_CHANGES, new JSONArray().toString());
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>(){}.getType();
        List<String> yourList = gson.fromJson(serverURL, type);
        return yourList;
    }

    public void setAllNetworkStatusChanges(String networkStatusChanges) {
        SharedPreferences.Editor sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext()).edit();
        sharedPreferencesEditor.putString(NETWORK_STATUS_CHANGES, networkStatusChanges);
        sharedPreferencesEditor.apply();
    }

    public boolean getIsAlaramManagerSet() {
        SharedPreferences sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        boolean isAlaramManagerSet = sharedPreferencesEditor.getBoolean(IS_ALARM_MANAGER_SET, false);
        return isAlaramManagerSet;
    }

    public void setIsAlaramManagerSet(boolean isAlaramManagerSet) {
        SharedPreferences.Editor sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext()).edit();
        sharedPreferencesEditor.putBoolean(IS_ALARM_MANAGER_SET, isAlaramManagerSet);
        sharedPreferencesEditor.apply();
    }



}
