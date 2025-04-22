package com.example.viewmodal;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.data.Task;
import com.example.respository.TaskRepository;

import java.util.List;

public class TaskViewModal extends ViewModel {
    private TaskRepository repository;
    private LiveData<List<Task>> listLiveData;

    public TaskViewModal(Application application) {
        this.repository=new TaskRepository(application);
        this.listLiveData=repository.getListTasks();
    }
    public void insert(Task task){
        repository.insert(task);
    }
    public void update(Task task){
        repository.update(task);
    }
    public void delete(Task task){
        repository.delete(task);
    }
}
