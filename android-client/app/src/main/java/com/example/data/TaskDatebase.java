package com.example.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class},version = 1)
public abstract class TaskDatebase extends RoomDatabase {
    public abstract TaskDao taskDao();
    private static volatile TaskDatebase INSTANCE;

    public static TaskDatebase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TaskDatebase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    TaskDatebase.class,
                                    "task_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
