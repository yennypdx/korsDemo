package com.android.kors.models;

import android.graphics.Bitmap;
import android.media.Image;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Group {

    @SerializedName("Id")
    @Expose
    private int Id;

    @SerializedName("GroupName")
    @Expose
    private String GroupName;

    @SerializedName("QrCode")
    @Expose
    private byte[] QrCode;

    public Group(){
        this.GroupName = "";
        this.QrCode = null;
    }

    public Group(byte[] image, String groupname){
        this.GroupName = groupname;
        this.QrCode = image;
    }

    public int getId() { return Id; }
    public void setId(int id) {
        Id = id;
    }

    public String getGroupName() { return GroupName; }
    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public byte[] getCodeImage() { return QrCode; }
    public void setCodeImage(byte[] codeImage) {
        QrCode = codeImage;
    }

}
