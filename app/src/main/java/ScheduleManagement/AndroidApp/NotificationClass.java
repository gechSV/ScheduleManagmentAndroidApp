package ScheduleManagement.AndroidApp;

import static android.app.NotificationChannel.DEFAULT_CHANNEL_ID;
import static android.provider.Settings.System.getString;

import static androidx.core.app.NotificationCompat.PRIORITY_HIGH;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.PriorityQueue;

import ScheduleManagement.AndroidApp.activity_controllers.MainActivity;

public class NotificationClass {
    private NotificationManager _notificationManager;
    private static final int NOTIFY_ID = 1;
    private static final String CHANEL_ID = "ScheduleManagerNotificationID";

    public NotificationClass(Context context){
        _notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void createNotificationChannel(NotificationManager manager) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANEL_ID , CHANEL_ID,
                    NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);

        }
    }


    public void  newNotification(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0, intent, PendingIntent.FLAG_IMMUTABLE);

        Calendar when = Calendar.getInstance();
        when.add(Calendar.MINUTE, 3);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, CHANEL_ID)
                .setAutoCancel(false)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setWhen(when.getTimeInMillis())
                .setContentIntent(pendingIntent)
                .setContentTitle("Title")
                .setContentText("some text")
                .setPriority(PRIORITY_HIGH);
        createNotificationChannel(_notificationManager);
        _notificationManager.notify(NOTIFY_ID, notificationBuilder.build());
    }
}
