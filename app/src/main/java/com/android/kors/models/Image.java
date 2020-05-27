package com.android.kors.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("Id")
    @Expose
    private int Id;

    @SerializedName("AssignmentId")
    @Expose
    private int AssignmentId;

    @SerializedName("Photo")
    @Expose
    private String Photo;

    public Image(){
        this.AssignmentId = 0;
        this.Photo = null;
    }

    public Image(int id, String image){
        this.AssignmentId = id;
        this.Photo = image;
    }

    public int getId() { return Id; }
    public void setId(int id) { Id = id; }

    public String getPhoto() { return Photo; }
    public void setPhoto(String image) { Photo = image; }

    public int getAssignmentId() { return AssignmentId; }
    public void setAssignmentId(int id) { AssignmentId = id; }
}
