package com.example.viewmodal;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.data.Task;
import com.example.respository.TaskRepository;

import java.util.List;

public class TaskViewModal extends ViewModel {
    private TaskRepository repository;

    public LiveData<List<Task>> getListLiveData() {
        return listLiveData;
    }

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
    public LiveData<Task> getTaskById(int id){
        return repository.get(id);
    }
}
