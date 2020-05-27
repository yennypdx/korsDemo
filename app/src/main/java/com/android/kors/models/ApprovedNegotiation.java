package com.android.kors.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApprovedNegotiation {
    @SerializedName("AssignmentId")
    @Expose
    private int AssignmentId;

    @SerializedName("Status")
    @Expose
    private String Status;

    @SerializedName("DateCompleted")
    @Expose
    private String DateCompleted;

    @SerializedName("TimeCompleted")
    @Expose
    private String TimeCompleted;

    public ApprovedNegotiation(){
        this.AssignmentId = 0;
        this.Status = "";
        this.DateCompleted = "";
        this.TimeCompleted = "";
    }

    public ApprovedNegotiation(int id, String stat, String date, String time){
        this.AssignmentId = id;
        this.Status = stat;
        this.DateCompleted = date;
        this.TimeCompleted = time;
    }

    public int getAssignmentId() { return AssignmentId; }
    public void setAssignmentId(int assignmentId) { AssignmentId = assignmentId; }

    public String getStatus() { return Status; }
    public void setStatus(String taskStatus) { Status = taskStatus; }

    public String getDateCompleted() {return DateCompleted;}
    public void setDateCompleted(String date){DateCompleted = date; }

    public String getTimeCompleted(){return TimeCompleted;}
    public void setTimeCompleted(String time){TimeCompleted = time; }
}
