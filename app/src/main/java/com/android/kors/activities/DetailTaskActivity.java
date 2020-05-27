package com.android.kors.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.kors.R;
import com.android.kors.helperClasses.Communicator;
import com.android.kors.helperClasses.DbHelper;
import com.android.kors.helperClasses.SetWallpaper;
import com.android.kors.models.Assignment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTaskActivity extends AppCompatActivity {
    private static final String DEBUG = "Debug Value";
    private Communicator communicator = new Communicator();
    Assignment theAssignment = new Assignment();
    private SetWallpaper sw = new SetWallpaper();
    String status = "";
    int asgID;
    int imgCount;
    int UserIdFromServer;
    int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);
        DbHelper db = new DbHelper(getApplicationContext());
        final int wallId = db.getCurrentWallpaperPreference();
        ViewGroup wallView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        sw.MyWall(wallId, wallView);

        asgID = getIntent().getIntExtra("AssignmentKey", 0);
        currentUserId = db.getCurrentUserId();

        final TextView statusDisplay = findViewById(R.id.statusResult);
        final TextView roomDisplay = findViewById(R.id.roomNameFinalOutput);
        final TextView taskDisplay = findViewById(R.id.taskNameFinalOutput);
        final TextView dateDisplay = findViewById(R.id.completionDateFinalOut);
        final TextView timeDisplay = findViewById(R.id.completionTimeFinalOut);
        final TextView assigneeDisplay = findViewById(R.id.assigneeFinalOutput);
        final TextView messageDisplay = findViewById(R.id.instructionFinalOutput);
        final Button confirmBtn = findViewById(R.id.confirmButton);
        final Button negotiateBtn = findViewById(R.id.negotiateButton);
        final Button completedButton = findViewById(R.id.completedButton);

        communicator.getImageCountForOneAssignment(asgID, new Callback<Long>() {
            @Override
            public void onResponse(@NonNull Call<Long> call, @NonNull Response<Long> response) {
                if(response.isSuccessful() && response.body() != null) {
                    Long tempNum = response.body();
                    imgCount = tempNum.intValue();
                } else {
                    Log.e("Error Code", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Long> call, @NonNull Throwable t) {
                Log.d(DEBUG, String.valueOf(t));
            }
        });

        // retrieve the assignment with its ID
        communicator.getSingleAssignmentByAssignmentId(asgID, new Callback<Assignment>() {
            @Override
            public void onResponse(@NonNull Call<Assignment> call, @NonNull Response<Assignment> response) {
                if(response.isSuccessful() && response.body() != null) {
                    theAssignment = response.body();
                    final int roomId = theAssignment.getRoomId();
                    final int taskId = theAssignment.getTaskId();
                    UserIdFromServer = theAssignment.getUserId();
                    if(currentUserId != UserIdFromServer){
                        confirmBtn.setVisibility(View.INVISIBLE);
                        negotiateBtn.setVisibility(View.INVISIBLE);
                        completedButton.setVisibility(View.INVISIBLE);
                    } else {
                        confirmBtn.setVisibility(View.VISIBLE);
                        negotiateBtn.setVisibility(View.VISIBLE);
                        completedButton.setVisibility(View.VISIBLE);
                    }
                    statusDisplay.setText(theAssignment.getTaskStatus());
                    dateDisplay.setText(theAssignment.getDateCompleted());
                    timeDisplay.setText(theAssignment.getTimeCompleted());
                    messageDisplay.setText(theAssignment.getMessage());
                    status = theAssignment.getTaskStatus();

                    // retrieve room name using Room ID
                    communicator.getRoomNameByRoomId(roomId, new Callback<String>() {
                        @Override
                        public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                            if(response.isSuccessful() && response.body() != null) {
                                String roomName = response.body();
                                roomDisplay.setText(roomName);
                                // retrieving task name from server
                                communicator.getTaskNameByTaskId(taskId, new Callback<String>() {
                                    @Override
                                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                                        if(response.isSuccessful() && response.body() != null) {
                                            String taskName = response.body();
                                            taskDisplay.setText(taskName);
                                            communicator.getOneUserNameByUserId(UserIdFromServer, new Callback<String>() {
                                                @Override
                                                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                                                    if(response.isSuccessful() && response.body() != null) {
                                                        String userName = response.body();
                                                        assigneeDisplay.setText(userName);

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
                                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                                        Log.d(DEBUG, String.valueOf(t));
                                    }
                                });

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
            public void onFailure(@NonNull Call<Assignment> call, @NonNull Throwable t) {
                Log.d(DEBUG, String.valueOf(t));
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(status.contains("Available Confirmed")){
                    confirmBtn.setEnabled(false);
                    Toast.makeText(getApplicationContext(),"Already confirmed", Toast.LENGTH_SHORT).show();
                } else if (status.contains("Closed")){
                    confirmBtn.setEnabled(false);
                    Toast.makeText(getApplicationContext(), "Already closed", Toast.LENGTH_SHORT).show();
                } else{
                    communicator.updateSingleAssignmentStatus(asgID, "Available Confirmed", new Callback<String>() {
                        @Override
                        public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Toast.makeText(getApplicationContext(),"Status updated", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("Error Code", String.valueOf(response.code()));
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                            Log.d(DEBUG, String.valueOf(t));
                        }
                    });

                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });

        negotiateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(status.contains("Negotiating") ) {
                    negotiateBtn.setEnabled(false);
                    Toast.makeText(getApplicationContext(), "Already being negotiated", Toast.LENGTH_SHORT).show();
                } else if (status.contains("Closed")){
                    negotiateBtn.setEnabled(false);
                    Toast.makeText(getApplicationContext(), "Already closed", Toast.LENGTH_SHORT).show();
                } else {
                    communicator.updateSingleAssignmentStatus(asgID, "Negotiating", new Callback<String>() {
                        @Override
                        public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Toast.makeText(getApplicationContext(),"Status updated", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("Error Code", String.valueOf(response.code()));
                            }
                        }
                        @Override
                        public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                            Log.d(DEBUG, String.valueOf(t));
                        }
                    });

                    Intent intent = new Intent(getApplicationContext(), NegotiationProposalActivity.class);
                    intent.putExtra("AssignmentKey", asgID);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });

        completedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(status.contains("Closed")){
                    completedButton.setEnabled(false);
                    Toast.makeText(getApplicationContext(),"Already closed", Toast.LENGTH_SHORT).show();

                } else {
                    if(imgCount >= 3) {  // max submission is 3x
                        completedButton.setEnabled(false);
                        Toast.makeText(getApplicationContext(),"You've reached maximum submission limit", Toast.LENGTH_SHORT).show();

                    } else {
                        Intent intent = new Intent(getApplicationContext(), CompletionReqApprovalActivity.class);
                        intent.putExtra("AssignmentKey", asgID);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }
            }
        });
    }
}
