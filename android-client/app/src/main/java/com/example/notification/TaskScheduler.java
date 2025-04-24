package com.example.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.data.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TaskScheduler {
    private final Context context;
    private final AlarmManager alarmManager;

    public TaskScheduler(Context context) {
        this.context = context;
        this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void scheduleTask(Task task) {
        long timeInMillis = getTimeInMillis(task.getDate(), task.getTime());

        if (timeInMillis > System.currentTimeMillis()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (!alarmManager.canScheduleExactAlarms()) {
                    // Permission not granted
                    Log.e("TaskScheduler", "No permission to schedule exact alarms");
                    // Consider asking user to grant permission
                    return;
                }
            }
            Intent intent = new Intent(context, TaskAlarmReceiver.class);
            intent.putExtra(TaskAlarmReceiver.EXTRA_TASK_ID, task.getId());
            intent.putExtra(TaskAlarmReceiver.EXTRA_TASK_TITLE, task.getTitle());
            intent.putExtra(TaskAlarmReceiver.EXTRA_TASK_DESCRIPTION, task.getDescription());
            intent.putExtra(TaskAlarmReceiver.EXTRA_TASK_DATE, task.getDate());
            intent.putExtra(TaskAlarmReceiver.EXTRA_TASK_TIME, task.getTime());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    context,
                    task.getId(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );
            // create alarm for next future with alarm Manger
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);

            Log.d("TaskScheduler", "Scheduled task: " + task.getTitle() + " for " + task.getDate() + " " + task.getTime());
        } else {
            Log.d("TaskScheduler", "Cannot schedule task in the past: " + task.getTitle());
        }
    }

    public void cancelTask(Task task) {
        Intent intent = new Intent(context, TaskAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                task.getId(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        alarmManager.cancel(pendingIntent);

        Log.d("TaskScheduler", "Canceled task: " + task.getTitle());
    }

    private long getTimeInMillis(String dateStr, String timeStr) {
        try {
            String dateTimeStr = dateStr + " " + timeStr;
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            Date date = format.parse(dateTimeStr);

            if (date != null) {
                return date.getTime();
            }
        } catch (ParseException e) {
            Log.e("TaskScheduler", "Error parsing date: " + e.getMessage());
        }
        return System.currentTimeMillis();
    }
}
