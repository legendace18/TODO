package com.ace.legend.todo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

/**
 * Created by rohan on 3/30/15.
 */
public class AlarmReceiver extends BroadcastReceiver {
    int notifyid;
    String title,detail,date,time;

    @Override
    public void onReceive(Context context, Intent intent) {

        title = intent.getStringExtra("Title");
        detail = intent.getStringExtra("Detail");
        date = intent.getStringExtra("Date");
        time = intent.getStringExtra("Time");
        notifyid = intent.getIntExtra("RequestCode", 0);
        Log.d("legend.ace18", "alarm" + notifyid);
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(
                context);
        nBuilder.setSmallIcon(R.drawable.ic_launcher);
        nBuilder.setContentTitle("ToDo");
        nBuilder.setContentText(title);

        // for sound and vibration
        Uri alarmSound = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        nBuilder.setSound(alarmSound).setVibrate(
                new long[] { 1000, 1000, 1000 });

        Intent notificationIntent = new Intent(context, TodoDetail.class);
        notificationIntent.putExtra("title", title);
        notificationIntent.putExtra("detail", detail);
        notificationIntent.putExtra("date", date);
        notificationIntent.putExtra("time", time);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent pi = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
        nBuilder.setContentIntent(pi);
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notifyid, nBuilder.build());

    }
}
