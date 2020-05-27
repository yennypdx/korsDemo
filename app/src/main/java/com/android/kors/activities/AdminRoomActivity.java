package com.android.kors.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.kors.R;
import com.android.kors.helperClasses.Communicator;
import com.android.kors.helperClasses.DbHelper;
import com.android.kors.helperClasses.SetWallpaper;
import com.android.kors.models.Assignment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminRoomActivity extends AppCompatActivity {
    private SetWallpaper sw = new SetWallpaper();
    private Communicator communicator = new Communicator();
    private static final String DEBUG = "Debug Value";
    int groupId;
    int approvalReqCount = 0;
    int negotiationReqCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_room);

        DbHelper db = new DbHelper(getApplicationContext());
        final int UserId = db.getCurrentUserId();
        final int wallId = db.getCurrentWallpaperPreference();
        ViewGroup wallView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        sw.MyWall(wallId, wallView);

        // TODO: access local db to get total from ApprovalNewNum and NegotiationNewNum to feed the badge textView

        communicator.getGroupIdByUserId(UserId, new Callback<Integer>() {
            @Override
            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                if(response.isSuccessful() && response.body() != null){
                    groupId = response.body();
                } else {
                    Log.e("Error Code", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                Log.d(DEBUG, String.valueOf(t));
            }
        });

        Button toApprovePage = findViewById(R.id.approvalListBtn);
        toApprovePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminRoomActivity.this, ApprovalReqListActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        Button toNegotiatePage = findViewById(R.id.negotiateListBtn);
        toNegotiatePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminRoomActivity.this, NegotiationReqListActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        Button toRemoveMemberPage = findViewById(R.id.removeMemberButton);
        toRemoveMemberPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminRoomActivity.this, RemoveMemberActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        Button toModifyHomePage = findViewById(R.id.modifyHomeBtn);
        toModifyHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminRoomActivity.this, HomePropertyTabsActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        Button toModifyMemberPage = findViewById(R.id.modifyMemberBtn);
        toModifyMemberPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminRoomActivity.this, ModifyMemberPermissionActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
