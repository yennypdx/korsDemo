package com.android.kors.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Negotiation {

    @SerializedName("Id")
    @Expose
    private int Id;

    @SerializedName("AssignmentId")
    @Expose
    private int AssignmentId;

    @SerializedName("Date")
    @Expose
    private String Date;

    @SerializedName("Time")
    @Expose
    private String Time;

    @SerializedName("Reason")
    @Expose
    private String Reason;

    @SerializedName("Status")
    @Expose
    private String Status;


    public Negotiation(){
        this.AssignmentId = 0;
        this.Date = "";
        this.Time = "";
        this.Reason ="";
        this.Status = "";
    }

    public Negotiation(int id, String date, String time, String msg){
        this.AssignmentId = id;
        this.Date = date;
        this.Time = time;
        this.Reason = msg;
        this.Status = "Negotiating";
    }

    public int getId() { return Id; }
    public void setId(int id) { Id = id; }

    public int getAssignmentId() { return AssignmentId; }
    public void setAssignmentId(int aid) {
        AssignmentId = aid;
    }

    public String getDate() {return Date;}
    public void setDate(String date) { Date = date; }

    public String getTime() { return Time; }
    public void setTime(String time) { Time = time; }

    public String getReason() { return Reason; }
    public void setReason(String reason) { Reason = reason; }

}
