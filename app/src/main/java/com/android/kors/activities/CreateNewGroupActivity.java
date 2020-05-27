package com.android.kors.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.kors.R;
import com.android.kors.helperClasses.DbHelper;
import com.android.kors.helperClasses.SetWallpaper;
import com.android.kors.models.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CreateNewGroupActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public static final String currentUserPreference = "korsPreference";
    private SetWallpaper sw = new SetWallpaper();
    private DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_group);

        db = new DbHelper(getApplicationContext());
        final int wallId = db.getCurrentWallpaperPreference();
        ViewGroup wallView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        sw.MyWall(wallId, wallView);

        sharedPreferences = getSharedPreferences(currentUserPreference, Context.MODE_PRIVATE);
        final String userName = sharedPreferences.getString("nameKey", null);
        final String userEmail = sharedPreferences.getString("emailKey", null);

        Button createNewButton = findViewById(R.id.createNew);
        createNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // save to sharedpref
                User newUser = new User("Admin", userName, userEmail);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(newUser);
                editor.putString("NewUser", json);
                editor.apply();

                storeTaskStatusList();
                startActivity(new Intent(CreateNewGroupActivity.this, GetAdminPhoneActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        Button joinAFamilyButton = findViewById(R.id.joinFamily);
        joinAFamilyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateNewGroupActivity.this, ScanGroupActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    void storeTaskStatusList(){
        List<String> statusList = new ArrayList<String>();
        statusList.add("Available Unconfirmed");
        statusList.add("Available Confirmed");
        statusList.add("Negotiating");
        statusList.add("Completed");
        statusList.add("Closed");

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set = new HashSet<String>(statusList);
        editor.putStringSet("TaskStatusNames", set);
        editor.apply();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
