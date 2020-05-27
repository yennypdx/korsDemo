package com.android.kors.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import com.android.kors.R;
import com.android.kors.helperClasses.Communicator;
import com.android.kors.helperClasses.DbHelper;
import com.android.kors.helperClasses.SetWallpaper;
import com.android.kors.models.TempData;
import com.android.kors.models.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetTaskListActivity extends AppCompatActivity {
    TableLayout ttContainer;
    View newRowView;
    View nextRowView;
    LayoutInflater inflater;
    List<EditText> taskList;
    private static final String DEBUG = "Debug Value";
    Communicator communicator = new Communicator();
    SharedPreferences sharedPreferences;
    private SetWallpaper sw = new SetWallpaper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_task_list);

        DbHelper db = new DbHelper(getApplicationContext());
        final int wallId = db.getCurrentWallpaperPreference();
        ViewGroup wallView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        sw.MyWall(wallId, wallView);

        final String groupName = getIntent().getStringExtra("gnameKey");
        final String phoneNum = getIntent().getStringExtra("phoneKey");

        taskList = new ArrayList<EditText>();
        ttContainer = findViewById(R.id.taskTypesEditTextContainer);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        newRowView = inflater.inflate(R.layout.new_edit_text, null);
        ttContainer.addView(newRowView);

        final EditText initBox = newRowView.findViewById(R.id.newEditText);
        initBox.requestFocus();

        Button addTaskBtn = findViewById(R.id.addTaskButton);
        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(taskList.size() == 0){
                    if(initBox.getText().toString().matches("")){
                        Toast.makeText(getApplicationContext(),
                                "Please fill the first text box", Toast.LENGTH_SHORT).show();
                    } else {
                        taskList.add(initBox);  // [0]
                        nextRowView = inflater.inflate(R.layout.new_edit_text, null);
                        ttContainer.addView(nextRowView);
                        EditText tempRoom = nextRowView.findViewById(R.id.newEditText);
                        tempRoom.requestFocus();
                        taskList.add(tempRoom); // [1]
                    }

                } else if (taskList.size() >= 2) {
                    if(taskList.get(taskList.size()-1).getText().toString().matches("")){
                        Toast.makeText(getApplicationContext(),
                                "Please enter a task name before adding new field", Toast.LENGTH_SHORT).show();
                    } else {
                        nextRowView = inflater.inflate(R.layout.new_edit_text, null);
                        ttContainer.addView(nextRowView);
                        EditText tempRoom = nextRowView.findViewById(R.id.newEditText);
                        tempRoom.requestFocus();
                        taskList.add(tempRoom);
                    }
                }
            }
        });

        Button deleteTaskBtn = findViewById(R.id.deleteTaskButton);
        deleteTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*int lastIdx = taskList.size();
            taskList.remove(lastIdx-1);
            ttContainer.removeView(nextRowView);
            ttContainer.invalidate();*/
                Toast.makeText(getApplicationContext(),
                        "Feature currently unavailable", Toast.LENGTH_SHORT).show();
            }
        });

        Button nextStepButton = findViewById(R.id.nextToGroupListFromTaskButton);
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> tasksNameList = new ArrayList<>();
                if(initBox.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext(),
                            "Please fill the first text box", Toast.LENGTH_SHORT).show();
                }else{
                    // save all rooms in the array list
                    for(int i = 0; i < taskList.size(); i++){
                        String taskName = taskList.get(i).getText().toString();
                        tasksNameList.add(taskName);
                    }

                    Gson gson = new Gson();
                    String userJson = sharedPreferences.getString("NewUser", null);
                    User outUser = gson.fromJson(userJson, User.class);
                    outUser.setPhone(phoneNum);
                    Set<String> roomsJson = sharedPreferences.getStringSet("RoomNames", null);
                    List<String> outRooms = new ArrayList<>(roomsJson);

                    final TempData outTempData = new TempData(outUser, groupName, outRooms, tasksNameList);
                    communicator.postAllTablesToServer(outTempData, new Callback<Void>() {
                        @Override
                        public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                            if(response.isSuccessful()) {
                                startActivity(new Intent(GetTaskListActivity.this, GroupListActivity.class));
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                            } else {
                                Log.e("Error Code", String.valueOf(response.code()));
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                            Log.d(DEBUG, String.valueOf(t));
                            startActivity(new Intent(GetTaskListActivity.this, WelcomeActivity.class));
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), GetRoomListActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
