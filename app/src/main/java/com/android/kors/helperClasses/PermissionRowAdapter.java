package com.android.kors.helperClasses;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.kors.R;
import com.android.kors.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PermissionRowAdapter extends ArrayAdapter<User> {
    private Communicator communicator = new Communicator();
    private static final String DEBUG = "Debug Value";
    private final List<User> theList;
    private final Context mContext;

    public PermissionRowAdapter(@NonNull Context context, List<User> UserList){
        super(context, R.layout.permission_row, UserList);
        this.mContext = context;
        this.theList = UserList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        RowObjectHolder rowHolder;

        if(rowView == null){
            rowView = LayoutInflater.from(mContext).inflate(R.layout.permission_row, parent, false);

            rowHolder = new RowObjectHolder();
            rowHolder.txtOut = rowView.findViewById(R.id.modifyRow_name);
            rowHolder.admin_chkBox = rowView.findViewById(R.id.modifyRow_admin_checkBox);
            rowHolder.member_chkBox = rowView.findViewById(R.id.modifyRow_member_checkBox);

            rowHolder.admin_chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    buttonView.setChecked(isChecked);
                    if(buttonView.isChecked()){
                        rowHolder.member_chkBox.setChecked(false);
                        rowHolder.member_chkBox.setEnabled(true);
                        rowHolder.admin_chkBox.setEnabled(false);
                    }
                }
            });

            rowHolder.admin_chkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    communicator.getUserIdByName(rowHolder.txtOut.getText().toString(), new Callback<Integer>() {
                        @Override
                        public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                            if(response.isSuccessful() && response.body() != null){
                                int uid = response.body();
                                communicator.getUserEmailByUserId(uid, new Callback<String>() {
                                    @Override
                                    public void onResponse(@NonNull Call<String> call,@NonNull Response<String> response) {
                                        if(response.isSuccessful() && response.body() != null){
                                            String userEmail = response.body();
                                            communicator.updateUserPosition(userEmail, "Admin", new Callback<Void>() {
                                                @Override
                                                public void onResponse(@NonNull Call<Void> call,@NonNull Response<Void> response) {
                                                    if(response.isSuccessful()){
                                                        Toast.makeText(mContext, "Permission updated", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Log.e("Error Code", String.valueOf(response.code()));
                                                    }
                                                }

                                                @Override
                                                public void onFailure(@NonNull Call<Void> call,@NonNull Throwable t) {
                                                    Log.d(DEBUG, String.valueOf(t));
                                                }
                                            });

                                        } else {
                                            Log.e("Error Code", String.valueOf(response.code()));
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<String> call,@NonNull Throwable t) {
                                        Log.d(DEBUG, String.valueOf(t));
                                    }
                                });
                            } else {
                                Log.e("Error Code", String.valueOf(response.code()));
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Integer> call,@NonNull Throwable t) {
                            Log.d(DEBUG, String.valueOf(t));
                        }
                    });
                }
            });

            rowHolder.member_chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    buttonView.setChecked(isChecked);
                    if(buttonView.isChecked()){
                        rowHolder.admin_chkBox.setChecked(false);
                        rowHolder.admin_chkBox.setEnabled(true);
                        rowHolder.member_chkBox.setEnabled(false);
                    }
                }
            });

            rowHolder.member_chkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    communicator.getUserIdByName(rowHolder.txtOut.getText().toString(), new Callback<Integer>() {
                        @Override
                        public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                            if(response.isSuccessful() && response.body() != null){
                                int uid = response.body();
                                communicator.getUserEmailByUserId(uid, new Callback<String>() {
                                    @Override
                                    public void onResponse(@NonNull Call<String> call,@NonNull Response<String> response) {
                                        if(response.isSuccessful() && response.body() != null){
                                            String userEmail = response.body();
                                            communicator.updateUserPosition(userEmail, "Member", new Callback<Void>() {
                                                @Override
                                                public void onResponse(@NonNull Call<Void> call,@NonNull Response<Void> response) {
                                                    if(response.isSuccessful()){
                                                        Toast.makeText(mContext, "Permission updated", Toast.LENGTH_LONG).show();
                                                    } else {
                                                        Log.e("Error Code", String.valueOf(response.code()));
                                                    }
                                                }

                                                @Override
                                                public void onFailure(@NonNull Call<Void> call,@NonNull Throwable t) {
                                                    Log.d(DEBUG, String.valueOf(t));
                                                }
                                            });

                                        } else {
                                            Log.e("Error Code", String.valueOf(response.code()));
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<String> call,@NonNull Throwable t) {
                                        Log.d(DEBUG, String.valueOf(t));
                                    }
                                });
                            } else {
                                Log.e("Error Code", String.valueOf(response.code()));
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Integer> call,@NonNull Throwable t) {
                            Log.d(DEBUG, String.valueOf(t));
                        }
                    });
                }
            });

            rowView.setTag(rowHolder);
        } else {
            rowHolder = (PermissionRowAdapter.RowObjectHolder)rowView.getTag();
        }

        // displaying name
        String outputStr = theList.get(position).getName();
        rowHolder.txtOut.setText(outputStr);

        // displaying initial position before changes
        if(theList.get(position).getPosition().equals("Admin")){
            rowHolder.admin_chkBox.setChecked(true);
            rowHolder.member_chkBox.setChecked(false);
        } else {
            rowHolder.member_chkBox.setChecked(true);
            rowHolder.admin_chkBox.setChecked(false);
        }

        return rowView;
    }

    public int getItemCount(){
        return (null != theList ? theList.size() : 0);
    }

    static class RowObjectHolder
    {
        TextView txtOut;
        CheckBox admin_chkBox;
        CheckBox member_chkBox;
    }
}
