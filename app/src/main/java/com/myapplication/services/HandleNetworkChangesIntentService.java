package com.myapplication.services;

import android.app.IntentService;
import android.content.Intent;

import com.google.gson.Gson;
import com.myapplication.AlarmManagerSchedule;
import com.myapplication.Data.ServerData;
import com.myapplication.MyApplication;
import com.myapplication.MyApplicationSharedPreferences;
import com.myapplication.Utils;
import com.myapplication.network.NetworkManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by hedi on 11/27/17.
 */

public class HandleNetworkChangesIntentService extends IntentService implements NetworkManager.NetworkManagerListener {

    public static final String TYPE_EXTRA = "type";
    public static final int TIME_TO_SEND_REPORTS_TO_SERVER = 5;

    private String url;
    private String uniqueID;


    @Override
    public void onCreate() {
        super.onCreate();

        url = ((MyApplication)(MyApplication.getAppContext())).getMyApplicationSharedPreferences().getServerURL();
        uniqueID = Utils.getPhoneIdentifier();

    }

    public HandleNetworkChangesIntentService() {
        super(HandleNetworkChangesIntentService.class.getName());
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        if(intent.getExtras() != null) {
            String type = intent.getExtras().getString(TYPE_EXTRA);
            NetworkManager.getInstance().setNetworkManagerListener(this);
            NetworkManager.getInstance().sendNetworkStatusToServer(url, type, uniqueID);
        }
    }

    @Override
    public void onResponseFromServerSucceeded(ServerData serverData) {
        updateReportsList();
    }

    private void updateReportsList() {
        final MyApplicationSharedPreferences applicationSharedPreferences = ((MyApplication)(MyApplication.getAppContext())).getMyApplicationSharedPreferences();
        List<String> list = applicationSharedPreferences.getAllNetworkStatusChanges();

        //will remove report from list
        if(!list.isEmpty()) {
            list.remove(0);
            String json = new Gson().toJson(list);
            applicationSharedPreferences.setAllNetworkStatusChanges(json);
        }
    }

    @Override
    public void onResponseFromServerFailed() {
        setTimerToStartNetworkCycle();
    }

    private void setTimerToStartNetworkCycle() {
        final MyApplicationSharedPreferences applicationSharedPreferences = ((MyApplication)(MyApplication.getAppContext())).getMyApplicationSharedPreferences();

        //will check every X minutes if server is on as long as one of the response failed. If push notification exists it can be an option too..
        if (!applicationSharedPreferences.getIsAlaramManagerSet())
        {
            applicationSharedPreferences.setIsAlaramManagerSet(true);
            new AlarmManagerSchedule().setAlaramClock(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(TIME_TO_SEND_REPORTS_TO_SERVER));
        }
    }

}
