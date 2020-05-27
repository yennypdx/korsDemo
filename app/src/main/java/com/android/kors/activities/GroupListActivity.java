package com.android.kors.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.kors.R;
import com.android.kors.helperClasses.Communicator;
import com.android.kors.helperClasses.DbHelper;
import com.android.kors.helperClasses.SetWallpaper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupListActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public static final String currentUserPreference = "korsPreference";
    private static final String DEBUG = "Debug Value";
    Communicator communicator = new Communicator();
    private SetWallpaper sw = new SetWallpaper();
    //List<Integer> groupIdList = new ArrayList<>();
    Button groupButton;
    Button createNewGroupButton;
    Button joinGroupButton;
    String initGroupString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        DbHelper db = new DbHelper(getApplicationContext());
        final int UserId = db.getCurrentUserId();
        final int wallId = db.getCurrentWallpaperPreference();
        ViewGroup wallView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        sw.MyWall(wallId, wallView);

        groupButton = findViewById(R.id.initGroup);
        sharedPreferences = getSharedPreferences(currentUserPreference, Context.MODE_PRIVATE);

        communicator.getGroupIdByUserId(UserId, new Callback<Integer>() {
            @Override
            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                if(response.isSuccessful() && response.body() != null) {
                    int groupId = response.body();
                    db.insertLocalGroupId(groupId);

                    communicator.getGroupNameByGroupId(groupId, new Callback<String>() {
                        @Override
                        public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                            if(response.isSuccessful() && response.body() != null) {
                                initGroupString = response.body();
                                groupButton.setText(initGroupString);
                                db.insertLocalCurrentGroupName(initGroupString);

                            } else {
                                Log.e("Error Code", String.valueOf(response.code()));
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                            Log.d(DEBUG, String.valueOf(t));
                        }
                    });

                } else {
                    Log.e("Error Code", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                Log.d(DEBUG, String.valueOf(t));
            }
        });

        groupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupListActivity.this, DashboardActivity.class);
                intent.putExtra("gname", initGroupString);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                /*if(groupIdList.size() == 0){
                    initGroupButton.setEnabled(false);
                }else{
                    startActivity(new Intent(GroupListActivity.this, DashboardActivity.class));
                }*/
            }
        });

        createNewGroupButton = findViewById(R.id.createNewInGroupList);
        createNewGroupButton.setVisibility(View.INVISIBLE);
        createNewGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GroupListActivity.this, CreateNewGroupActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        joinGroupButton = findViewById(R.id.joinFamilyInGroupList);
        joinGroupButton.setVisibility(View.INVISIBLE);
        joinGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GroupListActivity.this, ScanGroupActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), GroupListActivity.class));
        Toast.makeText(getApplicationContext(),"End of back action", Toast.LENGTH_SHORT).show();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
