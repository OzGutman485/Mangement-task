package com.example.ui;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.data.Task;
import com.example.home.R;
import com.example.notification.TaskScheduler;
import com.example.viewmodal.TaskViewModal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeTask extends AppCompatActivity {
    private TaskViewModal taskViewModel;
    public static final int ADD_TASK_REQUEST = 1;
    public static final int EDIT_TASK_REQUEST = 2;
    private static final int NOTIFICATION_PERMISSION_CODE = 123;
    private TaskScheduler taskScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_home);
        requestNotificationPermission();
        requestExactAlarmPermission();
        this.taskScheduler= new TaskScheduler(this);
        FloatingActionButton floatingActionButton=findViewById(R.id.addTaskButton);
        floatingActionButton.setOnClickListener(v->{
            Intent intent=new Intent(this, Add_Task.class);
            startActivityForResult(intent, ADD_TASK_REQUEST);
        });
        RecyclerView recyclerView = findViewById(R.id.tasksRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TaskAdapter adapter = new TaskAdapter();
        recyclerView.setAdapter(adapter);

        taskViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(TaskViewModal.class);
        taskViewModel.getListLiveData().observe(this, tasks ->{
            adapter.submitList(tasks);
            if (tasks != null) {
                for (Task task : tasks) {
                    if (!task.getIsdone()) {
                        taskScheduler.scheduleTask(task);
                    }
                }
            }
        });


        adapter.setOnItemClickListener(task -> {
            Intent intent = new Intent(this, Add_Task.class);
            intent.putExtra("id", task.getId());
            intent.putExtra("title", task.getTitle());
            intent.putExtra("description", task.getDescription());
            intent.putExtra("date", task.getDate());
            intent.putExtra("time", task.getTime());
            startActivityForResult(intent, EDIT_TASK_REQUEST);
        });
        adapter.setOnItemCheckListener((task, isChecked) -> {
            task.setIsdone(isChecked);
            taskViewModel.update(task);
            if (isChecked) {
                taskScheduler.cancelTask(task);
            } else {
                taskScheduler.scheduleTask(task);
            }
        });
        adapter.setOnItemDeleteListener(task -> taskViewModel.delete(task));
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_CODE);
            }
        }
    }
    private void requestExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (!alarmManager.canScheduleExactAlarms()) {
                Intent intent = new Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TASK_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("description");
            String date = data.getStringExtra("date");
            String time = data.getStringExtra("time");

            Task task = new Task(false, date, time, description,title);
            taskViewModel.insert(task);
        } else if (requestCode == EDIT_TASK_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra("id", -1);

            if (id == -1) {
                return;
            }

            String title = data.getStringExtra("title");
            String description = data.getStringExtra("description");
            String date = data.getStringExtra("date");
            String time = data.getStringExtra("time");

            Task task = new Task(false,date,time,description,title);
            task.setId(id);
            taskViewModel.update(task);
        }
    }
}