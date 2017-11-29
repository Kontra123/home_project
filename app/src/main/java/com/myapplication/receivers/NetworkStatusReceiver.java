package com.myapplication.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.myapplication.MyApplication;
import com.myapplication.MyApplicationSharedPreferences;
import com.myapplication.services.ServiceManager;
import com.myapplication.UI.DeviceNetworkStatusFragment;
import com.myapplication.Utils;
import com.myapplication.network.NetworkManager;

import java.util.List;

/**
 * Created by hedi on 11/27/17.
 */

public class NetworkStatusReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        String lastDeviceNetworkStatus = NetworkManager.getInstance().getDeviceNetoworkSate();

        String newDeviceNetworkStatus = Utils.getDeviceNetworkStatus(context);
        NetworkManager.getInstance().setDeviceNetoworkSate(newDeviceNetworkStatus);

        //we want to update UI, and update server only if device network status was changed
        if(!lastDeviceNetworkStatus.equals(newDeviceNetworkStatus)) {

            List<String> list = updateReportList(newDeviceNetworkStatus);

            //check if network avaliable
            if (!newDeviceNetworkStatus.equals(Utils.NO_CONNECTION)) {
                for (int i = 0; i < list.size(); i++) {
                    new ServiceManager().startNetworkChangeService(context, list.get(i));
                }
            }

            //will cause UI to be updated
            Intent networkStatusChangeIntent = new Intent(DeviceNetworkStatusFragment.DEVICE_STATUS_CHANGE_FILTER);
            LocalBroadcastManager.getInstance(MyApplication.getAppContext()).sendBroadcast(networkStatusChangeIntent);

        }

    }

    @NonNull
    private List<String> updateReportList(String newDeviceNetworkStatus) {
        final MyApplicationSharedPreferences applicationSharedPreferences = ((MyApplication)(MyApplication.getAppContext())).getMyApplicationSharedPreferences();
        List<String> list = applicationSharedPreferences.getAllNetworkStatusChanges();
        list.add(newDeviceNetworkStatus);
        String json = new Gson().toJson(list);
        applicationSharedPreferences.setAllNetworkStatusChanges(json);
        return list;
    }
}