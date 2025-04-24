package com.example.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.data.Task;
import com.example.home.R;
import com.example.ui.HomeTask;

public class NotificationHelper {
    private static final String CHANNEL_ID="reminder channel";
    private static final String CHANNEL_NAME= "Task Reminders";
    private static final String CHANNEL_DESCRIPTION = "Notifications for task reminders";
    private final NotificationManager notificationManager;
    private final Context context;

    public NotificationHelper(Context context) {
        this.context=context;
        this.notificationManager=(NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        createNotifcationChannel();
    }

    private void createNotifcationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationChannel.setDescription(CHANNEL_DESCRIPTION);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
    public void showTaskNotification(Task task){
        Intent intent=new Intent(context, HomeTask.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                task.getId(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(task.getTitle())
                .setContentText(task.getDescription())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        notificationManager.notify(task.getId(),builder.build());

    }
}
