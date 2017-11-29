package com.myapplication.services;

import android.content.Context;
import android.content.Intent;

/**
 * Created by AmirG on 11/29/2017.
 */
public class ServiceManager {

    public void startNetworkChangeService(Context context, String type) {
        Intent intent = new Intent(context, HandleNetworkChangesIntentService.class);
        intent.putExtra(HandleNetworkChangesIntentService.TYPE_EXTRA, type);
        context.startService(intent);
    }

}
