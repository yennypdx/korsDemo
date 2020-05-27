package com.android.kors.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Room {

    @SerializedName("Id")
    @Expose
    private int Id;

    @SerializedName("RoomName")
    @Expose
    private String RoomName;

    @SerializedName("GroupId")
    @Expose
    private int GroupId;

    public Room(){
        this.RoomName = "";
        this.GroupId = 0;
    }

    public Room(String roomname, int groupId){
        this.RoomName = roomname;
        this.GroupId = groupId;
    }

    public int getId() { return Id; }
    public void setId(int id) {
        Id = id;
    }

    public String getRoomName() { return RoomName; }
    public void setRoomName(String roomName) {
        RoomName = roomName;
    }

    public int getGroupId() { return GroupId; }
    public void setGroupId(int id) {
        GroupId = id;
    }
}


