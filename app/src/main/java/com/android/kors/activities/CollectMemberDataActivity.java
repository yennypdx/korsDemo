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
import android.widget.EditText;
import android.widget.TextView;

import com.android.kors.R;
import com.android.kors.helperClasses.Communicator;
import com.android.kors.helperClasses.DbHelper;
import com.android.kors.helperClasses.SetWallpaper;
import com.android.kors.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectMemberDataActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public static final String TAG = "CollectMemberDataActivity";
    public static final String currentUserPreference = "korsPreference";
    private static final String DEBUG = "Debug Value";
    Communicator communicator = new Communicator();
    private SetWallpaper sw = new SetWallpaper();
    private DbHelper db;
    int GroupID;
    int UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_member_data);
        db = new DbHelper(getApplicationContext());
        final int wallId = db.getCurrentWallpaperPreference();
        ViewGroup wallView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        sw.MyWall(wallId, wallView);

        sharedPreferences = getSharedPreferences(currentUserPreference, Context.MODE_PRIVATE);
        final String userName = sharedPreferences.getString("nameKey", null);
        final String userEmail = sharedPreferences.getString("emailKey", null);
        final String groupName = getIntent().getStringExtra("gnameKey");

        TextView nameFromGoogle = findViewById(R.id.googleNameInput);
        nameFromGoogle.setText(userName);
        TextView emailFromGoogle = findViewById(R.id.googleEmailInput);
        emailFromGoogle.setText(userEmail);
        final EditText phoneInputFromUser = findViewById(R.id.phoneDataInput);

        Button nextButton = findViewById(R.id.nextToGroupListFromScan);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNum = phoneInputFromUser.getText().toString();
                User newMember = new User("Member", userName, phoneNum, userEmail);

                communicator.postInsertInitMemberUserData(newMember, new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        if(response.isSuccessful()) {
                            Log.w(TAG, "New member insertion success");

                            communicator.getUserIdByEmail(userEmail, new Callback<Integer>() {
                                @Override
                                public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                                    if(response.isSuccessful() && response.body() != null) {
                                        UserID = response.body();
                                        db.insertLocalUserId(UserID);

                                        communicator.getGroupIdByGroupName(groupName, new Callback<Integer>() {
                                            @Override
                                            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                                                if(response.isSuccessful() && response.body() != null) {
                                                    GroupID = response.body();

                                                    communicator.postInsertUserGroupPair(UserID, GroupID, new Callback<Void>() {
                                                        @Override
                                                        public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                                                            if(response.isSuccessful()) {
                                                                startActivity(new Intent(getApplicationContext(), GroupListActivity.class));
                                                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                                                            } else {
                                                                Log.e("Error Code", String.valueOf(response.code()));
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
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

                                    } else {
                                        Log.e("Error Code", String.valueOf(response.code()));
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                                    Log.d(DEBUG, String.valueOf(t));
                                }
                            });
                        } else {
                            Log.e("Error Code", String.valueOf(response.code()));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        Log.d(DEBUG, String.valueOf(t));
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), ScanGroupActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
