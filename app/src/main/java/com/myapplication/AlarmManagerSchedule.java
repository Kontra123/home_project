package com.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.myapplication.receivers.TimerReceiver;

/**
 * Created by AmirG on 11/29/2017.
 */
public class AlarmManagerSchedule {

    public static final int ALARM_CODE = 12;

    public void setAlaramClock(long timeToWakeup)
    {
        AlarmManager am = (AlarmManager) MyApplication.getAppContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MyApplication.getAppContext(), TimerReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(MyApplication.getAppContext(), ALARM_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.setAlarmClock(new AlarmManager.AlarmClockInfo(timeToWakeup ,pi), pi);
    }




}
