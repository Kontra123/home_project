package com.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.myapplication.network.NetworkManager;

import java.util.List;

/**
 * Created by hedi on 11/27/17.
 */

public class Utils {

    public static final String NO_CONNECTION = "No_Connection";

    public static String getPhoneIdentifier() {

        return Settings.Secure.getString(MyApplication.getAppContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getDeviceNetworkStatus(Context context) {

        String currectDeviceStatus = "";

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet

            // connected to wifi
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                currectDeviceStatus = activeNetwork.getTypeName();

            }

            // connected to the mobile provider's data plan
            else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                currectDeviceStatus = activeNetwork.getTypeName();
            }
        }

        else {

            // not connected to the internet
            currectDeviceStatus = NO_CONNECTION;

        }

        return currectDeviceStatus;
    }

}
