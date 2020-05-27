package com.android.kors.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.kors.R;
import com.android.kors.helperClasses.Communicator;
import com.android.kors.helperClasses.DbHelper;
import com.android.kors.helperClasses.SetWallpaper;

public class SettingsActivity extends AppCompatActivity {

    private Communicator communicator = new Communicator();
    private SetWallpaper sw = new SetWallpaper();
    private DbHelper db;
    private CheckBox checkText, checkEmail, checkNone;
    private Spinner spinnerWallpaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        db = new DbHelper(getApplicationContext());

        checkText = findViewById(R.id.checkbox_default);
        checkEmail = findViewById(R.id.checkbox_email);
        checkNone = findViewById(R.id.checkbox_none);

        final int wallId = db.getCurrentWallpaperPreference();
        final ViewGroup wallView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        sw.MyWall(wallId, wallView);

        int notifid = db.getCurrentNotificationPreference();
        if(notifid == 1){
            checkText.setChecked(false);
            checkEmail.setChecked(false);
            checkNone.setChecked(true);
        } else if(notifid == 2){
            checkText.setChecked(true);
            checkEmail.setChecked(false);
            checkNone.setChecked(false);
        } else if(notifid == 3){
            checkText.setChecked(false);
            checkEmail.setChecked(true);
            checkNone.setChecked(false);
        } else {
            checkText.setChecked(true);
            checkEmail.setChecked(true);
            checkNone.setChecked(false);
        }

        checkText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkText.isChecked()){
                    checkText.setChecked(true);
                    checkNone.setChecked(false);
                } else {
                    checkText.setChecked(false);
                }
            }
        });

        checkEmail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkEmail.isChecked()){
                    checkEmail.setChecked(true);
                    checkNone.setChecked(false);
                } else {
                    checkEmail.setChecked(false);
                }
            }
        });

        checkNone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkNone.isChecked()){
                    checkNone.setChecked(true);
                    checkText.setChecked(false);
                    checkEmail.setChecked(false);
                } else {
                    checkNone.setChecked(false);
                }
            }
        });

        Button notifSettingButton = findViewById(R.id.setting_save_button);
        notifSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkNone.isChecked()){
                    db.updateNotificationPreference(1);
                } else if (!checkText.isChecked() && checkEmail.isChecked()){
                    db.updateNotificationPreference(3);
                } else if (checkText.isChecked() && checkEmail.isChecked()){
                    db.updateNotificationPreference( 4);
                } else {
                    db.updateNotificationPreference(2);
                }
                Toast.makeText(getApplicationContext(), "Notification setting is updated", Toast.LENGTH_SHORT).show();
            }
        });

        spinnerWallpaper = findViewById(R.id.spinner_wallpaper);

        Button wallpaperSettingButton = findViewById(R.id.wallpaper_setting_button);
        wallpaperSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wallChoice = spinnerWallpaper.getSelectedItem().toString();
                int wallid = db.getCurrentWallpaperPreference();
                int newWallId = 0;

                if(wallChoice.equals("Kors")){
                    if(wallid == 1){
                        Toast.makeText(getApplicationContext(), "Your current is already on Default", Toast.LENGTH_SHORT).show();
                    } else {
                        newWallId = 1;
                        db.updateWallpaperPreference(newWallId);
                        sw.MyWall(newWallId, wallView);
                    }

                } else if (wallChoice.equals("Undertale")) {
                    if (wallid == 2) {
                        Toast.makeText(getApplicationContext(), "Your current is already Undertale", Toast.LENGTH_SHORT).show();
                    } else {
                        newWallId = 2;
                        db.updateWallpaperPreference(newWallId);
                        sw.MyWall(newWallId, wallView);
                    }
                } else if (wallChoice.equals("Tokyo Ghoul")) {
                    if (wallid == 3) {
                        Toast.makeText(getApplicationContext(), "Your current is already Tokyo Ghoul", Toast.LENGTH_SHORT).show();
                    } else {
                        newWallId = 3;
                        db.updateWallpaperPreference(newWallId);
                        sw.MyWall(newWallId, wallView);
                    }
                } else if (wallChoice.equals("Samsung Galaxy")) {
                    if (wallid == 4) {
                        Toast.makeText(getApplicationContext(), "Your current is already Samsung Galaxy", Toast.LENGTH_SHORT).show();
                    } else {
                        newWallId = 4;
                        db.updateWallpaperPreference(newWallId);
                        sw.MyWall(newWallId, wallView);
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
