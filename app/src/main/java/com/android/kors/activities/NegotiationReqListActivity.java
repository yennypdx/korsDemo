package com.android.kors.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.kors.R;
import com.android.kors.helperClasses.Communicator;
import com.android.kors.helperClasses.DbHelper;
import com.android.kors.helperClasses.SetWallpaper;
import com.android.kors.helperClasses.TaskRowAdapter;
import com.android.kors.models.Assignment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NegotiationReqListActivity extends AppCompatActivity {
    Communicator communicator = new Communicator();
    private static final String DEBUG = "Debug Value";
    List<Assignment> assignmentList = new ArrayList<>();
    TaskRowAdapter adapter;
    ListView negotiatedList;
    private SetWallpaper sw = new SetWallpaper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negotiation_req_list);

        DbHelper db = new DbHelper(getApplicationContext());
        final int wallId = db.getCurrentWallpaperPreference();
        ViewGroup wallView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        sw.MyWall(wallId, wallView);

        negotiatedList = findViewById(R.id.negotiatedAssignmentListView);
        communicator.getCompletedAssignmentListForAdminToApprove("Negotiating", new Callback<List<Assignment>>(){
            @Override
            public void onResponse(@NonNull Call<List<Assignment>> call, @NonNull Response<List<Assignment>> response) {
                if(response.isSuccessful()) {
                    if(response.body() != null){
                        assignmentList = response.body();
                        adapter = new TaskRowAdapter(getApplicationContext(), assignmentList);
                        negotiatedList.setAdapter(adapter);

                    } else{
                        Toast.makeText(getApplicationContext(),"Currently you have no negotiation request", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("Error Code", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Assignment>> call, @NonNull Throwable t) {
                Log.d(DEBUG, String.valueOf(t));
            }
        } );

        negotiatedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int assignmentKey = assignmentList.get(position).getId();
                Intent intent = new Intent(getApplicationContext(), DetailNegotiatedAssignmentActivity.class);
                intent.putExtra("AssignmentKey", assignmentKey);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
