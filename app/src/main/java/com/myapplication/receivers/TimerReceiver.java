package com.myapplication.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.myapplication.MyApplication;
import com.myapplication.MyApplicationSharedPreferences;
import com.myapplication.services.ServiceManager;

import java.util.List;

/**
 * Created by AmirG on 11/29/2017.
 */
public class TimerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        final MyApplicationSharedPreferences applicationSharedPreferences = ((MyApplication)(MyApplication.getAppContext())).getMyApplicationSharedPreferences();
        applicationSharedPreferences.setIsAlaramManagerSet(false);

        List<String> list = applicationSharedPreferences.getAllNetworkStatusChanges();

        //send failed reports
        for (String type :list) {
            new ServiceManager().startNetworkChangeService(context, type);
        }
    }
}
