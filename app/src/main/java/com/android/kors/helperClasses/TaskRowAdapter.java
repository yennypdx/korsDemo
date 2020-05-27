package com.android.kors.helperClasses;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.kors.R;
import com.android.kors.models.Assignment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskRowAdapter extends ArrayAdapter<Assignment> {
    private final List<Assignment> assignList;
    private final Context mContext;
    private static final String DEBUG = "Debug Value";
    private Communicator communicator = new Communicator();

    public TaskRowAdapter(@NonNull Context context, List<Assignment> theList){
        super(context, 0, theList);
        this.mContext = context;
        this.assignList = theList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        if(rowView == null){
            rowView = LayoutInflater.from(mContext).inflate(R.layout.task_row, parent, false);
        }

        Assignment assignment = assignList.get(position);
        DbHelper db = new DbHelper(mContext);

        final TextView roomNameText = rowView.findViewById(R.id.room_name_inRow);
        final int roomVal = assignment.getRoomId();
        final TextView taskNameText = rowView.findViewById(R.id.task_name_inRow);
        final int taskVal = assignment.getTaskId();
        final TextView userNameText = rowView.findViewById(R.id.child_name_inRow);
        final int userVal = assignment.getUserId();

        // retrieving room name from server
        communicator.getRoomNameByRoomId(roomVal, new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(response.isSuccessful() && response.body() != null) {
                    String roomName = response.body();
                    roomNameText.setText(roomName);

                    // retrieving task name from server
                    communicator.getTaskNameByTaskId(taskVal, new Callback<String>() {
                        @Override
                        public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                            if(response.isSuccessful() && response.body() != null) {
                                String taskName = response.body();
                                taskNameText.setText(taskName);

                                // retrieving user name from server
                                communicator.getOneUserNameByUserId(userVal, new Callback<String>() {
                                    @Override
                                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                                        if(response.isSuccessful()) {
                                            if(response.body() != null){
                                                String userName = response.body();
                                                userNameText.setText(userName);
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
        return rowView;
    }
}
