package com.myapplication.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AmirG on 11/28/2017.
 */
public class ServerData {

    @SerializedName("msg")
    @Expose
    public String msg;
}
