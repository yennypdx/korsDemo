package com.android.kors.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.android.kors.helperClasses.Message;
import com.android.kors.helperClasses.MessagingService;
import com.android.kors.helperClasses.SetWallpaper;
import com.android.kors.models.ApprovedNegotiation;
import com.android.kors.models.Assignment;
import com.android.kors.models.Negotiation;
import com.android.kors.models.SubmissionUpdate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailNegotiatedAssignmentActivity extends AppCompatActivity {
    private Communicator communicator = new Communicator();
    private MessagingService ms = new MessagingService();
    private SetWallpaper sw = new SetWallpaper();
    private static final String DEBUG = "Debug Value";
    Assignment theAssignment = new Assignment();
    int asgId;
    String newDate;
    String newTime;
    String userPhone;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_negotiated_assignment);
        DbHelper db = new DbHelper(getApplicationContext());
        final int wallId = db.getCurrentWallpaperPreference();
        ViewGroup wallView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        sw.MyWall(wallId, wallView);

        asgId = getIntent().getIntExtra("AssignmentKey",0);
        int UserId = db.getCurrentUserId();
        communicator.getUserEmailByUserId(UserId, new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call,  @NonNull Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userEmail = response.body();
                } else {
                    Log.e("Error Code", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call,  @NonNull Throwable t) {
                Log.d(DEBUG, String.valueOf(t));
            }
        });

        final TextView assigneeDisplay = findViewById(R.id.Negotiated_assigneeTextView);
        final TextView roomDisplay = findViewById(R.id.Negotiated_roomNameTextView);
        final TextView taskDisplay = findViewById(R.id.Negotiated_taskNameTextview);
        final TextView dateDisplay = findViewById(R.id.Negotiated_completionDateFinalOut);
        final TextView timeDisplay = findViewById(R.id.Negotiated_completionTimeFinalOut);
        final TextView instructDisplay = findViewById(R.id.Negotiated_instructionFinalOutput);
        final TextView proposedDateDisplay = findViewById(R.id.Negotiated_new_completionDateFinalOut);
        final TextView proposedTimeDisplay = findViewById(R.id.Negotiated_new_completionTimeFinalOut);
        final TextView reasonDisplay = findViewById(R.id.Negotiated_reason_Output);

        // retrieve the assignment with its ID
        communicator.getSingleAssignmentByAssignmentId(asgId, new Callback<Assignment>() {
            @Override
            public void onResponse(@NonNull Call<Assignment> call, @NonNull Response<Assignment> response) {
                if(response.isSuccessful() && response.body() != null) {
                    theAssignment = response.body();
                    final int roomId = theAssignment.getRoomId();
                    final int taskId = theAssignment.getTaskId();
                    final int assigneeUserId = theAssignment.getUserId();
                    dateDisplay.setText(theAssignment.getDateCompleted());
                    timeDisplay.setText(theAssignment.getTimeCompleted());
                    instructDisplay.setText(theAssignment.getMessage());

                    GettingUserPhoneNumber(assigneeUserId);
                    communicator.getRoomNameByRoomId(roomId, new Callback<String>() {
                        @Override
                        public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                            if(response.isSuccessful() && response.body() != null) {
                                String roomName = response.body();
                                roomDisplay.setText(roomName);

                                communicator.getTaskNameByTaskId(taskId, new Callback<String>() {
                                    @Override
                                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                                        if(response.isSuccessful() && response.body() != null) {
                                            String taskName = response.body();
                                            taskDisplay.setText(taskName);

                                            communicator.getOneUserNameByUserId(assigneeUserId, new Callback<String>() {
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

        communicator.getNegotiationData(asgId, new Callback<Negotiation>() {
            @Override
            public void onResponse(@NonNull Call<Negotiation> call, @NonNull Response<Negotiation> response) {
                if(response.isSuccessful() && response.body() != null) {
                    Negotiation nego = response.body();
                    newDate = nego.getDate();
                    newTime = nego.getTime();
                    String reason = nego.getReason();

                    proposedDateDisplay.setText(newDate);
                    proposedTimeDisplay.setText(newTime);
                    reasonDisplay.setText(reason);

                } else {
                    Log.e("Error Code", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Negotiation> call, @NonNull Throwable t) {
                Log.d(DEBUG, String.valueOf(t));
            }
        });

        final int NotifPref = db.getCurrentNotificationPreference();
        Button acceptNegoBtn = findViewById(R.id.acceptNegoButton);
        acceptNegoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApprovedNegotiation update = new ApprovedNegotiation(asgId, "Available Confirmed", newDate, newTime);
                communicator.updateApprovedNewDateTimeStatus(update , new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            String messageAccept = Message.getNegotiatedAcceptText();
                            if(NotifPref == 2){
                                ms.sendClientTextMessage(userPhone, messageAccept);
                            } else if(NotifPref == 3){
                                ms.sendMemberClientEmailMessage(userEmail, messageAccept);
                            } else if(NotifPref == 4){
                                ms.sendClientTextMessage(userPhone, messageAccept);
                                ms.sendMemberClientEmailMessage(userEmail, messageAccept);
                            }

                        } else {
                            Log.e("Error Code", String.valueOf(response.code()));
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        Log.d(DEBUG, String.valueOf(t));
                    }
                });
                startActivity(new Intent(getApplicationContext(), NegotiationReqListActivity.class));
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        Button declineNegoBtn = findViewById(R.id.rejectNegoButton);
        declineNegoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                communicator.updateSingleAssignmentStatus(asgId, "Available Unconfirmed", new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            String messageDecline = Message.getNegotiatedDeclineText();
                            if(NotifPref == 2){
                                ms.sendClientTextMessage(userPhone, messageDecline);
                            } else if(NotifPref == 3){
                                ms.sendMemberClientEmailMessage(userEmail, messageDecline);
                            } else if(NotifPref == 4){
                                ms.sendClientTextMessage(userPhone, messageDecline);
                                ms.sendMemberClientEmailMessage(userEmail, messageDecline);
                            }

                        } else {
                            Log.e("Error Code", String.valueOf(response.code()));
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        Log.d(DEBUG, String.valueOf(t));
                    }
                });
                startActivity(new Intent(getApplicationContext(), NegotiationReqListActivity.class));
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    private void GettingUserPhoneNumber(int uid){
        communicator.getUserPhoneNumberById(uid, new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userPhone = response.body();
                } else {
                    Log.e("Error Code", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.d(DEBUG, String.valueOf(t));
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), NegotiationReqListActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
