package com.example.myapplication;
import static android.content.Context.MODE_PRIVATE;
import static com.example.myapplication.RandomActivity.factor_first;
import static com.example.myapplication.RandomActivity.factor_one;
import static com.example.myapplication.RandomActivity.factor_second;
import static com.example.myapplication.RandomActivity.factor_two;
import static com.example.myapplication.RandomActivity.l;
import static com.example.myapplication.RandomActivity.previous;
import static com.example.myapplication.RandomActivity.problem;
import static com.example.myapplication.RandomDataActivity.increment;
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

public class AlarmReceiverforHT extends BroadcastReceiver {
    public static String CHANNEL_ID = "CHANNEL_ID_BETTER DECISION";
    //you can add any id you want
    public static String symbol = " ";
    @Override
    public void onReceive(Context context, Intent intent) {
        intent.getAction();
        Integer rq = intent.getIntExtra("requestCode", 0);
        Integer index = intent.getIntExtra("put", 0);
        String variable_fir = intent.getStringExtra("variable1");
        String variable_sec = intent.getStringExtra("variable2");
        String direction = intent.getStringExtra("direction");
        Integer totaldata = intent.getIntExtra("totaldata", 0);
        String type = intent.getStringExtra("type");
        Integer variablecount = intent.getIntExtra("variablecount", 0);
        Intent notificationInt = new Intent(context, DataActivity.class);//on tap this activity will open
        String action = "com.dataActivity." + String.valueOf(index);
        notificationInt.putExtra("name", variablecount);
        notificationInt.putExtra("variable_1", variable_fir);
        notificationInt.putExtra("variable_2", variable_sec);
        notificationInt.putExtra("direction", direction);
        notificationInt.putExtra("type", type);
        notificationInt.putExtra("totaldata", totaldata);
        notificationInt.setAction(action);
        notificationInt.putExtra("put", index);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(DataActivity.class);
        stackBuilder.addNextIntent(notificationInt);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(rq, PendingIntent.FLAG_UPDATE_CURRENT);
        String name = type + " " + "of" + " " + variable_fir + " " + direction + " " + type + " " + "of" + " " + variable_sec;
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

        increment++;

    }


}
