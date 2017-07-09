package com.example.admin.todomanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

public class AlarmReciever extends BroadcastReceiver {
static  int i=0;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String title=intent.getStringExtra("name");
        Toast.makeText(context,"Set!!",Toast.LENGTH_LONG).show();
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context).setContentTitle("My Notifications")
                .setContentText("Time for "+ title)
                .setAutoCancel(true).setSmallIcon(android.R.drawable.ic_menu_report_image).setDefaults(Notification.DEFAULT_SOUND);
        Intent resultIntent = new Intent(context,MainActivity.class);

        PendingIntent resultpendingIntent=PendingIntent.getActivity(context,i++,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultpendingIntent);
        NotificationManager mnotificationManager =(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        mnotificationManager.notify(i++,mBuilder.build());
    }
}
