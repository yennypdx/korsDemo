package com.android.kors.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TempData {

    @SerializedName("User")
    @Expose
    private User User;

    @SerializedName("GroupName")
    @Expose
    private String GroupName;

    @SerializedName("RoomNames")
    @Expose
    private List<String> RoomNames;

    @SerializedName("TaskNames")
    @Expose
    private List<String> TaskNames;

    public TempData(){
        this.User = null;
        this.GroupName = "";
        this.RoomNames = null;
        this.TaskNames = null;
    }

    public TempData(User user, String groupName, List<String> room, List<String> task){
        this.User = user;
        this.GroupName = groupName;
        this.RoomNames = room;
        this.TaskNames = task;
    }

    public User getUser() { return User; }
    public void setUser(User user) { User = user; }

    public String getGroup() { return GroupName; }
    public void setGroup(String groupName) { GroupName = groupName; }

    public List<String> getRoomNames() { return RoomNames; }
    public void setRoomNames(List<String> roomNames) { RoomNames = roomNames; }

    public List<String> getTaskNames() { return TaskNames; }
    public void setTaskNames(List<String> taskNames) { TaskNames = taskNames; }
}
