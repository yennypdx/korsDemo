package com.android.kors.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmissionUpdate {

    @SerializedName("AssignmentId")
    @Expose
    private int AssignmentId;

    @SerializedName("Status")
    @Expose
    private String Status;

    @SerializedName("SubmissionDate")
    @Expose
    private String SubmissionDate;

    @SerializedName("SubmissionTime")
    @Expose
    private String SubmissionTime;

    public SubmissionUpdate(){
        this.AssignmentId = 0;
        this.Status = "";
        this.SubmissionDate = "";
        this.SubmissionTime = "";
    }

    public SubmissionUpdate(int id, String stat, String date, String time){
        this.AssignmentId = id;
        this.Status = stat;
        this.SubmissionDate = date;
        this.SubmissionTime = time;
    }

    public int getAssignmentId() { return AssignmentId; }
    public void setAssignmentId(int assignmentId) { AssignmentId = assignmentId; }

    public String getStatus() { return Status; }
    public void setStatus(String taskStatus) { Status = taskStatus; }

    public String getSubmissionDate() {return SubmissionDate;}
    public void setSubmissionDate(String date){SubmissionDate = date; }

    public String getSubmissionTime(){return SubmissionTime;}
    public void setSubmissionTime(String time){SubmissionTime = time; }
}
