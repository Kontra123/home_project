package com.myapplication.UI;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.myapplication.MyApplication;
import com.myapplication.MyApplicationSharedPreferences;
import com.myapplication.R;
import com.myapplication.Utils;
import com.myapplication.network.NetworkManager;

/**
 * Created by AmirG on 11/28/2017.
 */
public class DeviceNetworkStatusFragment extends Fragment {

    public static final String DEVICE_STATUS_CHANGE_FILTER = "DEVICE_STATUS_CHANGE_FILTER";
    private TextView currentNetworkStatusTextView;

    public static DeviceNetworkStatusFragment newInstance() {
        DeviceNetworkStatusFragment fragment = new DeviceNetworkStatusFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(connectionStatus, new IntentFilter(DEVICE_STATUS_CHANGE_FILTER));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_screen_fragment, container, false);

        final MyApplicationSharedPreferences applicationSharedPreferences = ((MyApplication)(MyApplication.getAppContext())).getMyApplicationSharedPreferences();

        final EditText serverUrlEditText = view.findViewById(R.id.main_screen_server_url_editext);
//        serverUrlEditText.setSelection(serverUrlEditText.getText().length());
        serverUrlEditText.setText(applicationSharedPreferences.getServerURL());
        serverUrlEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                // will save server name to shared shared preferences only if user pressed on done button
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    applicationSharedPreferences.setServerURL(v.getText().toString());
                }
                return false;
            }
        });

        currentNetworkStatusTextView = view.findViewById(R.id.main_screen_current_network_status_value_text);

        String deviceNetworkStatus = Utils.getDeviceNetworkStatus(getActivity());
        NetworkManager.getInstance().setDeviceNetoworkSate(deviceNetworkStatus);

        updateUI();

        return view;
    }

    private BroadcastReceiver connectionStatus = new BroadcastReceiver() {


        @Override
        public void onReceive(Context context, Intent intent) {
            if(isAdded()) {
                updateUI();
            }
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(connectionStatus);
    }

    private void updateUI() {

        String currentDeviceNetoworkSate = NetworkManager.getInstance().getDeviceNetoworkSate();
        currentNetworkStatusTextView.setText(currentDeviceNetoworkSate);

    }
}
