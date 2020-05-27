package com.android.kors.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.kors.R;
import com.android.kors.helperClasses.Communicator;
import com.android.kors.helperClasses.DbHelper;
import com.android.kors.helperClasses.PermissionRowAdapter;
import com.android.kors.helperClasses.SetWallpaper;
import com.android.kors.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyMemberPermissionActivity extends AppCompatActivity {
    private SetWallpaper sw = new SetWallpaper();
    private Communicator communicator = new Communicator();
    private static final String DEBUG = "Debug Value";
    PermissionRowAdapter permissionAdapter;
    final List<User> UserNameAndPositionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_member_permission);

        DbHelper db = new DbHelper(getApplicationContext());
        final int wallId = db.getCurrentWallpaperPreference();
        ViewGroup wallView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        sw.MyWall(wallId, wallView);

        ListView allMemberListView = findViewById(R.id.modifyMemberListView);
        communicator.getUserNameAndPositionList(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if(response.isSuccessful() && response.body() != null){
                    UserNameAndPositionList.addAll(response.body());
                    permissionAdapter = new PermissionRowAdapter(getApplicationContext(), UserNameAndPositionList);
                    allMemberListView.setAdapter(permissionAdapter);

                } else {
                    Log.e("Error Code", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                Log.d(DEBUG, String.valueOf(t));
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), AdminRoomActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
