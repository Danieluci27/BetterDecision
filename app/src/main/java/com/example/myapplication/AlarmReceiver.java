package com.example.myapplication;
import static android.content.Context.MODE_PRIVATE;
import static com.example.myapplication.RandomActivity.factor_first;
import static com.example.myapplication.RandomActivity.factor_one;
import static com.example.myapplication.RandomActivity.factor_second;
import static com.example.myapplication.RandomActivity.factor_two;
import static com.example.myapplication.RandomActivity.l;
import static com.example.myapplication.RandomActivity.previous;
import static com.example.myapplication.RandomActivity.problem;
import static com.example.myapplication.SubActivity.factor_count;
import static com.example.myapplication.SubActivity.sp_factor_count;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;

public class AlarmReceiver extends BroadcastReceiver {
    public static String CHANNEL_ID = "CHANNEL_ID_BETTER DECISION";
    //you can add any id you want
    @Override
    public void onReceive(Context context, Intent intent) {
        //NO NEED TO MAKE SEPARATE ALARM RECEIVER FOR HT AND PD. ADD 'HT' AT THE LAST OF
        //INTENT MESSAGE AND SEND A MESSAGE. ADD 'PD' IF FROM PD CLASS.
        //EX) FACTOR1 = "YUSUDJJJHT" -> "YUSUDJJJHTHT" -> ONLY CHECK THE LAST TWO SO HT IN ORIGINAL
        //STRING DOESN'T MATTER
        //THEN MAKE TWO SEPARATE FUNCTIONS INSIDE THIS CLASS TO DO PROCEDURE UNIQUE FOR EACH CLASS.
        intent.getAction();
        Integer rq = intent.getIntExtra("requestCode", 0);
        Integer index = intent.getIntExtra("put", 0);
        String factor_fir = intent.getStringExtra("factor1");
        String factor_sec = intent.getStringExtra("factor2");
        Integer factorcount = intent.getIntExtra("factorcount", 0);
        Integer datacount = intent.getIntExtra("datacount", 0);
        Intent notificationInt = new Intent(context, ThirdActivity.class);//on tap this activity will open
        String get_it = "com.getIt." + String.valueOf(index);
        notificationInt.putExtra("name", factorcount);
        notificationInt.putExtra("factor_1", factor_fir);
        notificationInt.putExtra("factor_2", factor_sec);
        notificationInt.putExtra("datacount", datacount);
        notificationInt.setAction(get_it);
        notificationInt.putExtra("put", index);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ThirdActivity.class);
        stackBuilder.addNextIntent(notificationInt);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(rq, PendingIntent.FLAG_ONE_SHOT);
        String name = factor_fir + " " + "vs" + " " + factor_sec;
        //getting the pendingIntent

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification notification = builder.setContentTitle(name)
                    .setContentText("It's a time to add the data!")
                    .setTicker("New Message Alert!")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent).build();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }

        String name_for_channel = "Data Day_" + String.valueOf(rq);

//below creating notification channel, because of androids latest update, O is Oreo

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    name_for_channel,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        int id = rq;
        notificationManager.notify(id, notification);

        problem++;

    }


}


