package com.android.kors.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.android.kors.R;
import com.android.kors.helperClasses.Communicator;
import com.android.kors.helperClasses.DbHelper;
import com.android.kors.helperClasses.ListViewItemCheckboxBaseAdapter;
import com.android.kors.helperClasses.ListViewItemDataObject;
import com.android.kors.helperClasses.SetWallpaper;
import com.android.kors.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoveMemberActivity extends AppCompatActivity {
    private SetWallpaper sw = new SetWallpaper();
    private static final String TAG1 = "DeletionProcess";
    private Communicator communicator = new Communicator();
    ListViewItemCheckboxBaseAdapter removeAdminAdapter;
    ListViewItemCheckboxBaseAdapter removeMemberAdapter;
    List<ListViewItemDataObject> initItemAdminList;
    ArrayList<ListViewItemDataObject> persistentAdminItemList;
    List<ListViewItemDataObject> initItemMemberList;
    ArrayList<ListViewItemDataObject> persistentMemberItemList;
    private static final String DEBUG = "Debug Value";
    ArrayList<String> adminList;
    ArrayList<String> memberList;
    String currentPhoneHolderName;
    int GroupId;
    String nameToDelete = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_member);

        DbHelper db = new DbHelper(getApplicationContext());
        final int CurrentUserId = db.getCurrentUserId();
        GroupId = db.getCurrentGroupId();
        final int wallId = db.getCurrentWallpaperPreference();
        ViewGroup wallView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        sw.MyWall(wallId, wallView);

        // get the user name via currentPhoneHolder's id
        communicator.getOneUserNameByUserId(CurrentUserId, new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(response.isSuccessful() && response.body() != null){
                    currentPhoneHolderName = response.body();
                } else {
                    Log.e("Error Code", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.d(DEBUG, String.valueOf(t));
            }
        });

        final List<User> allUser = new ArrayList<>();
        ListView adminListView = findViewById(R.id.listViewAdmin);
        ListView memberListView = findViewById(R.id.listViewMember);

        communicator.getUserNameAndPositionList(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if(response.isSuccessful() && response.body() != null){
                    allUser.addAll(response.body());
                    adminList = new ArrayList<>();
                    memberList = new ArrayList<>();

                    for(int i = 0; i < allUser.size(); i++){
                        String name = allUser.get(i).getName();

                        if(allUser.get(i).getPosition().equals("Admin")){
                            adminList.add(name);
                        } else {
                            memberList.add(name);
                        }
                    }

                    // ADMIN Checkbox handler
                    getInitViewItemDataObjectList(adminList, 1);
                    removeAdminAdapter = new ListViewItemCheckboxBaseAdapter(RemoveMemberActivity.this, initItemAdminList);
                    removeAdminAdapter.notifyDataSetChanged();
                    adminListView.setAdapter(removeAdminAdapter);

                    adminListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Object itemObject = parent.getAdapter().getItem(position);
                            final ListViewItemDataObject itemDataObject = (ListViewItemDataObject)itemObject;
                            CheckBox inRowCheckbox = view.findViewById(R.id.checkBoxTabItem);

                            if(itemDataObject.isChecked()){
                                inRowCheckbox.setChecked(false);
                                itemDataObject.setChecked(false);
                                initItemAdminList.get(position).setChecked(false);
                            } else {
                                inRowCheckbox.setChecked(true);
                                itemDataObject.setChecked(true);
                                initItemAdminList.get(position).setChecked(true);
                            }
                        }
                    });

                    // MEMBER Checkbox handler
                    getInitViewItemDataObjectList(memberList, 2);
                    removeMemberAdapter = new ListViewItemCheckboxBaseAdapter(RemoveMemberActivity.this, initItemMemberList);
                    removeMemberAdapter.notifyDataSetChanged();
                    memberListView.setAdapter(removeMemberAdapter);

                    memberListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Object itemObject = parent.getAdapter().getItem(position);
                            final ListViewItemDataObject itemDataObject = (ListViewItemDataObject)itemObject;
                            CheckBox inRowCheckbox = view.findViewById(R.id.checkBoxTabItem);

                            if(itemDataObject.isChecked()){
                                inRowCheckbox.setChecked(false);
                                itemDataObject.setChecked(false);
                                initItemMemberList.get(position).setChecked(false);
                            } else {
                                inRowCheckbox.setChecked(true);
                                itemDataObject.setChecked(true);
                                initItemMemberList.get(position).setChecked(true);
                            }
                        }
                    });
                } else {
                    Log.e("Error Code", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                Log.d(DEBUG, String.valueOf(t));
            }
        });

        Button removeBtn = findViewById(R.id.removeMemberButton);
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalUserCount = allUser.size();
                int isCheckedCount = 0;

                for(int a = 0; a < initItemAdminList.size(); a++){
                    ListViewItemDataObject adminCheckBox = initItemAdminList.get(a);
                    if(adminCheckBox.isChecked()){
                        nameToDelete = adminCheckBox.getItemText();
                        isCheckedCount++;
                    }
                }

                for(int m = 0; m < initItemMemberList.size(); m++){
                    ListViewItemDataObject memberCheckBox = initItemMemberList.get(m);
                    if(memberCheckBox.isChecked()){
                        nameToDelete = memberCheckBox.getItemText();
                        isCheckedCount++;
                    }
                }

                // make sure no all-user deletion is happening
                if(totalUserCount == isCheckedCount){
                    Toast.makeText(getApplicationContext(), "You cannot delete all members from the group", Toast.LENGTH_SHORT).show();

                    // auto un-checked all checkboxes
                    for(int a = 0; a < initItemAdminList.size(); a++){
                        ListViewItemDataObject adminCheckBox = initItemAdminList.get(a);
                        adminCheckBox.setChecked(false);
                    }

                    for(int m = 0; m < initItemMemberList.size(); m++){
                        ListViewItemDataObject memberCheckBox = initItemMemberList.get(m);
                        memberCheckBox.setChecked(false);
                    }
                }
                else if(isCheckedCount == 0){
                    Toast.makeText(getApplicationContext(), "Please check a name to delete", Toast.LENGTH_SHORT).show();
                }
                else if(isCheckedCount > 1){
                    Toast.makeText(getApplicationContext(), "You cannot delete more than 1 user at a time", Toast.LENGTH_SHORT).show();
                }
                else if(isCheckedCount == 1) {

                    final AlertDialog alertDialog = new AlertDialog.Builder(RemoveMemberActivity.this).create();
                    alertDialog.setTitle("Warning Message");
                    alertDialog.setMessage("Are you sure to remove " + nameToDelete + " from your group?");
                    alertDialog.setIcon(R.mipmap.warning);

                    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ArrayList<String> newList = new ArrayList<>();

                            if(adminList.contains(nameToDelete)){
                                int size = initItemAdminList.size();
                                newList.addAll(adminList);

                                for(int i = 0; i < size; i++){
                                    ListViewItemDataObject adminNameCheckBox = initItemAdminList.get(i);
                                    if(adminNameCheckBox.isChecked()){
                                        String name = adminNameCheckBox.getItemText();
                                        newList.remove(i);

                                        initItemAdminList.remove(i);
                                        i--;
                                        size = initItemAdminList.size();
                                    }
                                }
                                removeAdminAdapter.notifyDataSetChanged();
                                updateNameListOnDisplay(newList, 1);
                            }
                            if(memberList.contains(nameToDelete)){
                                int size = initItemMemberList.size();
                                newList.addAll(memberList);

                                for(int i = 0; i < size; i++){
                                    ListViewItemDataObject memberNameCheckBox = initItemMemberList.get(i);
                                    if(memberNameCheckBox.isChecked()){
                                        String name = memberNameCheckBox.getItemText();
                                        newList.remove(i);

                                        initItemMemberList.remove(i);
                                        i--;
                                        size = initItemMemberList.size();
                                    }
                                }
                                removeAdminAdapter.notifyDataSetChanged();
                                updateNameListOnDisplay(newList,2);
                            }

                            // get the communicator to user in db bi userId
                            communicator.getUserIdByName(nameToDelete, new Callback<Integer>() {
                                @Override
                                public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                                    if(response.isSuccessful() && response.body() != null){
                                        int uid = response.body();
                                        communicator.deleteUserById(uid, new Callback<Void>() {
                                            @Override
                                            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                                                if(response.isSuccessful()){
                                                    Log.i(TAG1, "Room deletion process successful");
                                                    // TODO: add toast for deleted action

                                                } else {
                                                    Log.e("Error Code", String.valueOf(response.code()));
                                                }
                                            }

                                            @Override
                                            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
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
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            }
        });
    }

    private void getInitViewItemDataObjectList(ArrayList<String> list, int type){
        int length = list.size();

        if(type == 1){
            initItemAdminList = new ArrayList<>();
            persistentAdminItemList = new ArrayList<>();
            persistentAdminItemList.addAll(initItemAdminList);

            for (int i = 0; i < length; i++){
                ListViewItemDataObject singleRowObject = new ListViewItemDataObject();
                String userName = list.get(i);
                singleRowObject.setChecked(false);
                singleRowObject.setItemText(userName);
                initItemAdminList.add(singleRowObject);
            }
        }
        if(type == 2){
            initItemMemberList = new ArrayList<>();
            persistentMemberItemList = new ArrayList<>();
            persistentMemberItemList.addAll(initItemMemberList);

            for (int i = 0; i < length; i++){
                ListViewItemDataObject singleRowObject = new ListViewItemDataObject();
                String userName = list.get(i);
                singleRowObject.setChecked(false);
                singleRowObject.setItemText(userName);
                initItemMemberList.add(singleRowObject);
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), AdminRoomActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void updateNameListOnDisplay(ArrayList<String> newList, int type){
        int length = newList.size();

        if(type == 1){
            initItemAdminList.clear();
            for(int i = 0; i < length; i++){
                ListViewItemDataObject singleRowObject = new ListViewItemDataObject();
                String roomName = newList.get(i);
                singleRowObject.setChecked(false);
                singleRowObject.setItemText(roomName);
                initItemAdminList.add(singleRowObject);
            }
            removeAdminAdapter.notifyDataSetChanged();

        } else if (type == 2) {
            initItemMemberList.clear();

            for(int i = 0; i < length; i++){
                ListViewItemDataObject singleRowObject = new ListViewItemDataObject();
                String roomName = newList.get(i);
                singleRowObject.setChecked(false);
                singleRowObject.setItemText(roomName);
                initItemMemberList.add(singleRowObject);
            }
            removeMemberAdapter.notifyDataSetChanged();
        }
    }
}
