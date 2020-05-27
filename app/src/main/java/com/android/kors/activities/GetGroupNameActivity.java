package com.android.kors.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.kors.R;
import com.android.kors.helperClasses.Communicator;
import com.android.kors.helperClasses.DbHelper;
import com.android.kors.helperClasses.SetWallpaper;

public class GetGroupNameActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    public static final String currentUserPreference = "korsPreference";

    Communicator communicator = new Communicator();
    EditText groupNameEditBox;
    private SetWallpaper sw = new SetWallpaper();
    private DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_group_name);

        db = new DbHelper(getApplicationContext());
        final int wallId = db.getCurrentWallpaperPreference();
        ViewGroup wallView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        sw.MyWall(wallId, wallView);

        final String phoneNum = getIntent().getStringExtra("phoneKey");
        sharedPreferences = getSharedPreferences(currentUserPreference, Context.MODE_PRIVATE);
        groupNameEditBox = findViewById(R.id.groupName_input);
        groupNameEditBox.requestFocus();

        Button nextToGetRoomsButton = findViewById(R.id.nextToGetRoomsButton);
        nextToGetRoomsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupName = groupNameEditBox.getText().toString();
                Intent intent = new Intent(GetGroupNameActivity.this, GetRoomListActivity.class);
                intent.putExtra("gnameKey", groupName);
                intent.putExtra("phoneKey", phoneNum);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), GetAdminPhoneActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
