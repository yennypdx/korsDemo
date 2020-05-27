package com.android.kors.helperClasses;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.kors.R;
import com.android.kors.activities.CalendarActivity;
import com.android.kors.models.Assignment;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<Assignment>> expandableListDetail;
    private static final String DEBUG = "Debug Value";
    private Communicator communicator = new Communicator();

    public CustomExpandableListAdapter(Context inContext, List<String> inTitleList, HashMap<String, List<Assignment>> inDetailList){
        this.context = inContext;
        this.expandableListTitle = inTitleList;
        this.expandableListDetail = inDetailList;
    }

    @Override
    public Object getChild(int listPosition, int expListPosition){
        return this.expandableListDetail
                .get(this.expandableListTitle.get(listPosition))
                .get(expListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expListPosition){
        return expListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expListPosition, boolean isLastChild, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.task_row, parent, false);
        }

        Assignment assignment = (Assignment) getChild(listPosition, expListPosition);
        final TextView roomNameText = convertView.findViewById(R.id.room_name_inRow);
        final int roomVal = assignment.getRoomId();
        final TextView taskNameText = convertView.findViewById(R.id.task_name_inRow);
        final int taskVal = assignment.getTaskId();
        final TextView userNameText = convertView.findViewById(R.id.child_name_inRow);
        final int userVal = assignment.getUserId();
        final TextView asgIdText = convertView.findViewById(R.id.asgId_inRow);
        final int id = assignment.getId();
        asgIdText.setText(String.valueOf(id));

        // retrieve room name from server
        communicator.getRoomNameByRoomId(roomVal, new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(response.isSuccessful() && response.body() != null) {
                    String roomName = response.body();
                    roomNameText.setText(roomName);

                    // retrieve task name from server
                    communicator.getTaskNameByTaskId(taskVal, new Callback<String>() {
                        @Override
                        public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                            if(response.isSuccessful() && response.body() != null) {
                                String taskName = response.body();
                                taskNameText.setText(taskName);

                                // retrieve user name from server
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
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition){
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).size();
    }

    @Override
    public Object getGroup(int listPosition){
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount(){
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition){
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent){
        String listTitle = (String) getGroup(listPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.group_list, null);
        }

        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
