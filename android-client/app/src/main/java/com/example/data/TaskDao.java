package com.example.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("Select * From task")
    LiveData<List<Task>> getTasks();

    @Query("Select * from task where task.id =:id")
    LiveData<Task> get(int id);
    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);
    @Query("SELECT * FROM task ORDER BY id DESC LIMIT 1")
    Task getLastInsertedTask();


}
