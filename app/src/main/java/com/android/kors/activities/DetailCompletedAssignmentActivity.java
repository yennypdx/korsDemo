package com.android.kors.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.kors.R;
import com.android.kors.helperClasses.Communicator;
import com.android.kors.helperClasses.DbHelper;
import com.android.kors.helperClasses.Message;
import com.android.kors.helperClasses.MessagingService;
import com.android.kors.helperClasses.SetWallpaper;
import com.android.kors.models.Assignment;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCompletedAssignmentActivity extends AppCompatActivity {
    private Communicator communicator = new Communicator();
    private MessagingService ms = new MessagingService();
    private SetWallpaper sw = new SetWallpaper();
    private static final String DEBUG = "Debug Value";
    Assignment oneAssignment;
    String userName;
    String roomName;
    String taskName;
    int AssignmentId;
    List<String> imageListInString = new ArrayList<>();
    List<Bitmap> bitmapList = new ArrayList<>();
    List<Bitmap> thumbnailList = new ArrayList<>();
    String CurrentStatus;
    ImageView imgBox1;
    ImageView imgBox2;
    ImageView imgBox3;
    String userPhone;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_completed_assignment);
        DbHelper db = new DbHelper(getApplicationContext());
        final int wallId = db.getCurrentWallpaperPreference();
        ViewGroup wallView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        sw.MyWall(wallId, wallView);

        AssignmentId = getIntent().getIntExtra("AssignmentKey", 0);
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

        imgBox1 = findViewById(R.id.submittedImage1);
        imgBox2 = findViewById(R.id.submittedImage2);
        imgBox3 = findViewById(R.id.submittedImage3);
        final TextView assigneeBox = findViewById(R.id.Completed_assigneeTextView);
        final TextView roomBox = findViewById(R.id.Completed_roomNameTextView);
        final TextView taskBox = findViewById(R.id.Completed_taskNameFinalOutput);
        final TextView submittedDateBox = findViewById(R.id.Completed_completionDateFinalOut);
        final TextView submittedTimeBox = findViewById(R.id.Completed_completionTimeFinalOut);
        final TextView instructBox = findViewById(R.id.Completed_instructionFinalOutput);

        // get the assignment specified by the assignment id
        communicator.getSingleAssignmentByAssignmentId(AssignmentId, new Callback<Assignment>() {
            @Override
            public void onResponse(@NonNull Call<Assignment> call, @NonNull Response<Assignment> response) {
                if(response.isSuccessful() && response.body() != null) {
                    oneAssignment = response.body();

                    final int assigneeUserId = oneAssignment.getUserId();
                    final int roomId = oneAssignment.getRoomId();
                    final int taskId = oneAssignment.getTaskId();
                    CurrentStatus = oneAssignment.getTaskStatus();
                    instructBox.setText(oneAssignment.getMessage());
                    submittedDateBox.setText(oneAssignment.getSubmissionDate());
                    submittedTimeBox.setText(oneAssignment.getSubmissionTime());

                    GettingUserPhoneNumber(assigneeUserId);
                    // breaking down components from assignment
                    communicator.getOneUserNameByUserId(assigneeUserId, new Callback<String>() {
                        @Override
                        public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                userName = response.body();
                                assigneeBox.setText(userName);

                                communicator.getRoomNameByRoomId(roomId, new Callback<String>() {
                                    @Override
                                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                                        if (response.isSuccessful() && response.body() != null) {
                                            roomName = response.body();
                                            roomBox.setText(roomName);

                                            communicator.getTaskNameByTaskId(taskId, new Callback<String>() {
                                                @Override
                                                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                                                    if (response.isSuccessful() && response.body() != null) {
                                                        taskName = response.body();
                                                        taskBox.setText(taskName);
                                                        GettingImagesFunc();

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

        final int NotifPref = db.getCurrentNotificationPreference();
        Button acceptCompletedBtn = findViewById(R.id.acceptButton);
        acceptCompletedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                communicator.updateSingleAssignmentStatus(AssignmentId, "Closed", new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            String messageAccept = Message.getCompletedAcceptText();
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
                startActivity(new Intent(getApplicationContext(), ApprovalReqListActivity.class));
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        Button declineCompletedBtn = findViewById(R.id.rejectButton);
        declineCompletedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                communicator.updateSingleAssignmentStatus(AssignmentId, "Available Unconfirmed", new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            String messageDecline = Message.getCompletedDeclineText();
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
                startActivity(new Intent(getApplicationContext(), ApprovalReqListActivity.class));
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        imgBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] imageBytes1 = bitmapToBytesConversion(0);

                Intent intent = new Intent(getApplicationContext(), EnlargedImageActivity.class);
                intent.putExtra("image", imageBytes1);
                intent.putExtra("AssignmentKey", AssignmentId);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        imgBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] imageBytes2 = bitmapToBytesConversion(1);
                Intent intent = new Intent(getApplicationContext(), EnlargedImageActivity.class);
                intent.putExtra("image", imageBytes2);
                intent.putExtra("AssignmentKey", AssignmentId);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        imgBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] imageBytes3 = bitmapToBytesConversion(2);
                Intent intent = new Intent(getApplicationContext(), EnlargedImageActivity.class);
                intent.putExtra("image", imageBytes3);
                intent.putExtra("AssignmentKey", AssignmentId);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    private byte[] bitmapToBytesConversion(int arrayPosition){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap img = bitmapList.get(arrayPosition);
        img.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imageBytes = stream.toByteArray();

        return imageBytes;
    }

    private void GettingImagesFunc(){
        // pulling images for this assignment
        communicator.getListOfImagesForOneAssignment(AssignmentId, new Callback<List<String>>() {
            @Override
            public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                if(response.isSuccessful()) {
                    if(response.body() != null){
                        imageListInString.addAll(response.body());

                        // decode images from String to bitmap then add to bitmap list
                        for (int i = 0; i < imageListInString.size(); i++) {
                            String image = imageListInString.get(i);
                            byte data[] = android.util.Base64.decode(image, Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                            bitmapList.add(bitmap);
                            Bitmap thumbnail = ThumbnailUtils.extractThumbnail(bitmap, 100, 100);
                            thumbnailList.add(thumbnail);
                        }

                        // display into image table layout
                        if(imageListInString.size() == 1){
                            imgBox1.setImageBitmap(bitmapList.get(0));
                        } else if(imageListInString.size() == 2){
                            imgBox1.setImageBitmap(bitmapList.get(0));
                            imgBox2.setImageBitmap(bitmapList.get(1));
                        } else{
                            imgBox1.setImageBitmap(bitmapList.get(0));
                            imgBox2.setImageBitmap(bitmapList.get(1));
                            imgBox3.setImageBitmap(bitmapList.get(2));
                        }
                    }
                } else {
                    Log.e("Error Code", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
                Log.d(DEBUG, String.valueOf(t));
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
        startActivity(new Intent(getApplicationContext(), ApprovalReqListActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
