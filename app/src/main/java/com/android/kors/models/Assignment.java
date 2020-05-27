package com.android.kors.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Assignment {

    @SerializedName("Id")
    @Expose
    private int Id;

    @SerializedName("RoomId")
    @Expose
    private int RoomId;

    @SerializedName("TaskId")
    @Expose
    private int TaskId;

    @SerializedName("UserId")
    @Expose
    private int UserId;

    @SerializedName("Status")
    @Expose
    private String Status;

    @SerializedName("DateCreated")
    @Expose
    private String DateCreated;

    @SerializedName("TimeCreated")
    @Expose
    private String TimeCreated;

    @SerializedName("DateCompleted")
    @Expose
    private String DateCompleted;

    @SerializedName("TimeCompleted")
    @Expose
    private String TimeCompleted;

    @SerializedName("Message")
    @Expose
    private String Message;

    @SerializedName("SubmissionDate")
    @Expose
    private String SubmissionDate;

    @SerializedName("SubmissionTime")
    @Expose
    private String SubmissionTime;

    public Assignment(){
        this.RoomId = 0;
        this.TaskId = 0;
        this.UserId = 0;
        this.Status = "";
        this.DateCreated = "";
        this.TimeCreated = "";
        this.DateCompleted = "";
        this.TimeCompleted = "";
        this.Message = "";
        this.SubmissionDate = "";
        this.SubmissionTime = "";
    }

    public Assignment(int roomid, int taskid, int userid, String status, String datecreated,
                      String timecreated, String datecompleted, String timecompleted,  String mssg){
        this.RoomId = roomid;
        this.TaskId = taskid;
        this.UserId = userid;
        this.Status = status;
        this.DateCreated = datecreated;
        this.TimeCreated = timecreated;
        this.DateCompleted = datecompleted;
        this.TimeCompleted = timecompleted;
        this.Message = mssg;
    }

    public int getId() { return Id; }
    public void setId(int id) { Id = id; }

    public int getRoomId() { return RoomId; }
    public void setRoomId(int roomId) {
        RoomId = roomId;
    }

    public int getTaskId() { return TaskId; }
    public void setTaskId(int taskId) {
        TaskId = taskId;
    }

    public int getUserId() { return UserId; }
    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getDateCreated() {return DateCreated;}
    public void setDateCreated(String dateCreated) { DateCreated = dateCreated; }

    public String getTimeCreated() {return TimeCreated;}
    public void setTimeCreated(String timeCreated) { TimeCreated = timeCreated; }

    public String getDateCompleted() {return DateCompleted;}
    public void setDateCompleted(String dateCompleted) { DateCompleted = dateCompleted; }

    public String getTimeCompleted() { return TimeCompleted; }
    public void setTimeCompleted(String timeCompleted) { TimeCompleted = timeCompleted; }

    public String getTaskStatus() { return Status; }
    public void setTaskStatus(String taskStatus) { Status = taskStatus; }

    public String getMessage() { return Message; }
    public void setMessage(String message) { Message = message; }

    public String getSubmissionDate() {return SubmissionDate;}
    public void setSubmissionDate(String date){SubmissionDate = date; }

    public String getSubmissionTime(){return SubmissionTime;}
    public void setSubmissionTime(String time){SubmissionTime = time; }

}
