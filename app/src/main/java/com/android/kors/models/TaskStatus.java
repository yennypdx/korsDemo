package com.android.kors.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaskStatus {

    @SerializedName("Id")
    @Expose
    private int Id;

    @SerializedName("TaskStatusName")
    @Expose
    private String TaskStatusName;

    public TaskStatus(){
        this.TaskStatusName = "";
    }

    public TaskStatus(String status){
        this.TaskStatusName = status;
    }

    public int getTaskStatusId() { return Id; }
    public void setTaskStatusId(int id) {
        Id = id;
    }

    public String getTaskStatusName() { return TaskStatusName; }
    public void setTaskStatusName(String taskName) {
        TaskStatusName = taskName;
    }
}
