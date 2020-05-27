package com.android.kors.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("Id")
    @Expose
    private int Id;

    @SerializedName("Position")
    @Expose
    private String Position;

    @SerializedName("Name")
    @Expose
    private String Name;

    @SerializedName("Phone")
    @Expose
    private String Phone;

    @SerializedName("Email")
    @Expose
    private String Email;

    public User(){
        this.Position = "";
        this.Name = "";
        this.Phone = "";
        this.Email = "";
    }

    public User(String position, String username, String email){
        this.Position = position;
        this.Name = username;
        this.Email = email;
    }

    public User(String position, String username, String phone, String email){
        this.Position = position;
        this.Name = username;
        this.Phone = phone;
        this.Email = email;
    }

    public int getId() { return Id; }
    public void setId(int id) { Id = id; }

    public String getPosition(){ return Position;}
    public void setPosition(String position) {
        Position = position;
    }

    public String getName(){return Name;}
    public void setName(String userName) {
        Name = userName;
    }

    public String getPhone(){return Phone;}
    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail(){return Email;}
    public void setEmail(String email) {
        Email = email;
    }
}
