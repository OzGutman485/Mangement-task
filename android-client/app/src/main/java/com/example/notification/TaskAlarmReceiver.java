package com.example.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.data.Task;

public class TaskAlarmReceiver extends BroadcastReceiver {
    public static final String EXTRA_TASK_ID = "task_id";
    public static final String EXTRA_TASK_TITLE = "task_title";
    public static final String EXTRA_TASK_DESCRIPTION = "task_description";
    public static final String EXTRA_TASK_DATE = "task_date";
    public static final String EXTRA_TASK_TIME = "task_time";

    @Override
    public void onReceive(Context context, Intent intent) {
        int taskId = intent.getIntExtra(EXTRA_TASK_ID, -1);
        String title = intent.getStringExtra(EXTRA_TASK_TITLE);
        String description = intent.getStringExtra(EXTRA_TASK_DESCRIPTION);
        String date = intent.getStringExtra(EXTRA_TASK_DATE);
        String time = intent.getStringExtra(EXTRA_TASK_TIME);

        if (taskId != -1 && title != null) {
            Task task = new Task(false, date, time, description, title);
            task.setId(taskId);
            NotificationHelper notificationHelper = new NotificationHelper(context);
            notificationHelper.showTaskNotification(task);
        }
    }
}
