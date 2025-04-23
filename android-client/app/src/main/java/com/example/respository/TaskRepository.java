package com.example.respository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.data.Task;
import com.example.data.TaskDao;
import com.example.data.TaskDatebase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRepository {
    private TaskDao taskDao;

    public LiveData<List<Task>> getListTasks() {
        return ListTasks;
    }

    private LiveData<List<Task>> ListTasks;
   private ExecutorService exceutor;
    public TaskRepository(Application application) {
        TaskDatebase td=TaskDatebase.getDatabase(application);
        this.taskDao=td.taskDao();
        this.ListTasks=taskDao.getTasks();
        this.exceutor= Executors.newSingleThreadExecutor();
    }
    public void update(Task task){
        exceutor.execute(() -> taskDao.update(task));
    }
    public void insert(Task task){
        exceutor.execute(()->taskDao.insert(task));
    }
    public void delete(Task task){
        exceutor.execute(()->taskDao.delete(task));
    }
    public LiveData<Task> get(int id){
        return taskDao.get(id);
    }
    //important! to use for in desotry of someView when i dont use more in the view and exuctor!
    public void shutdown(){
        exceutor.shutdown();
    }


}
