package com.myapplication.network;

import com.myapplication.Data.ServerData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;


/**
 * Created by hedi on 11/27/17.
 */

public interface IRequests {

    @GET
    Call<ServerData> sendDeviceStatusChangedRequest(@Url String url, @Query("Type") String type, @Query("UniqueId") String uniqueId);

}


