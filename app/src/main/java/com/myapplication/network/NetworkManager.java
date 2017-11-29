package com.myapplication.network;

import com.myapplication.Data.ServerData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hedi on 11/27/17.
 */

public class NetworkManager {

    public static final int RESPONSE_OK = 200;
    private IRequests apiInterface = RequestClient.getClient().create(IRequests.class);
    private NetworkManagerListener networkManagerListener;
    private String deviceNetoworkSate;


    public static synchronized NetworkManager getInstance()
    {
        return SingeltonHolder.NETWORK_MANAGER;
    }

    private static class SingeltonHolder
    {
        private static final NetworkManager NETWORK_MANAGER = new NetworkManager();
    }

    public interface NetworkManagerListener {
        public void onResponseFromServerSucceeded(ServerData serverData);
        public void onResponseFromServerFailed();
    }

    public void setNetworkManagerListener(NetworkManagerListener networkManagerListener) {
        this.networkManagerListener = networkManagerListener;
    }

    public void sendNetworkStatusToServer(String url, String type, String id) {
        Call<ServerData> call = apiInterface.sendDeviceStatusChangedRequest(url, type, id);
        call.enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(Call<ServerData> call, Response<ServerData> response) {

                ServerData serverData = response.body();
                int responseCode = response.code();
                if(responseCode == RESPONSE_OK) {
                    networkManagerListener.onResponseFromServerSucceeded(serverData);
                }
                else {
                    networkManagerListener.onResponseFromServerFailed();
                }
            }

            @Override
            public void onFailure(Call<ServerData> call, Throwable t) {

                call.cancel();

                networkManagerListener.onResponseFromServerFailed();
            }
        });
    }

    public String getDeviceNetoworkSate() {
        return deviceNetoworkSate;
    }

    public void setDeviceNetoworkSate(String deviceNetoworkSate) {
        this.deviceNetoworkSate = deviceNetoworkSate;
    }
}
