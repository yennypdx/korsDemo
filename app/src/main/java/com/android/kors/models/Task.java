package com.android.kors.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Task {

    @SerializedName("Id")
    @Expose
    private int Id;

    @SerializedName("TaskName")
    @Expose
    private String TaskName;

    @SerializedName("GroupId")
    @Expose
    private int GroupId;

    public Task(){
        this.TaskName = "";
        this.GroupId = 0;
    }

    public Task(String taskname, int groupId){
        this.TaskName = taskname;
        this.GroupId = groupId;
    }

    public int getId() { return Id; }
    public void setId(int id) {
        Id = id;
    }

    public String getTaskName() { return TaskName; }
    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    public int getGroupId() { return GroupId; }
    public void setGroupId(int id) {
        GroupId = id;
    }
}
