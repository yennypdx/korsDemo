package com.android.kors.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserGroup {

    @SerializedName("Id")
    @Expose
    private int Id;

    @SerializedName("UserId")
    @Expose
    private int UserId;

    @SerializedName("GroupId")
    @Expose
    private int GroupId;

    public UserGroup(){
        this.UserId = 0;
        this.GroupId = 0;
    }

    public UserGroup(int userid, int groupid){
        this.UserId = userid;
        this.GroupId = groupid;
    }

    public int getUserGroupId() { return Id; }
    public void setUserGroupId(int userGroupId) { Id = userGroupId; }

    public int getUserId() { return UserId; }
    public void setUserId(int userId) { UserId = userId; }

    public int getGroupId() { return GroupId; }
    public void setGroupId(int groupId) { GroupId = groupId; }
}
