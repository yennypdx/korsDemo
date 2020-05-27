package com.android.kors.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListModel {
    @SerializedName("GroupId")
    @Expose
    private int GroupId;

    @SerializedName("ListObj")
    @Expose
    private List<String> ListObj;

    public ListModel(){
        this.GroupId = 0;
        this.ListObj = null;
    }

    public ListModel(int gid, List<String> list){
        this.GroupId = gid;
        this.ListObj = list;
    }

    public int getGroupId() {
        return GroupId;
    }

    public void setGroupId(int groupId) {
        GroupId = groupId;
    }

    public List<String> getListObj() {
        return ListObj;
    }

    public void setListObj(List<String> listObj) {
        ListObj = listObj;
    }
}
