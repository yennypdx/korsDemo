package com.android.kors.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.kors.R;
import com.android.kors.helperClasses.Communicator;
import com.android.kors.helperClasses.DbHelper;
import com.android.kors.helperClasses.ListViewItemCheckboxBaseAdapter;
import com.android.kors.helperClasses.ListViewItemDataObject;
import com.android.kors.helperClasses.SetWallpaper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditRoomActivity extends AppCompatActivity {
    private Communicator communicator = new Communicator();
    private SetWallpaper sw = new SetWallpaper();
    private static final String TAG1 = "DeletionProcess";
    private static final String TAG2 = "AdditionProcess";
    private static final String DEBUG = "Debug Value";
    ViewFlipper viewFlipper;
    LayoutInflater inflater;
    View newRowView;
    View nextRowView;
    List<EditText> roomListEditText;
    ArrayList<String> roomNameStrListFromServer = new ArrayList<>();
    TableLayout tableContainer;
    List<ListViewItemDataObject> initItemList;
    ListViewItemCheckboxBaseAdapter roomAdapter;
    ArrayList<ListViewItemDataObject> persistentItemList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.edit_room_listview);
        viewFlipper = findViewById(R.id.roomTabViewFlipper);

        DbHelper db = new DbHelper(getApplicationContext());
        final int wallId = db.getCurrentWallpaperPreference();
        ViewGroup wallView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        sw.MyWall(wallId, wallView);

        final int GroupId = db.getCurrentGroupId();
        final ListView roomListView = findViewById(R.id.roomListViewTab);

        communicator.getRoomNameListByGroupId(GroupId, new Callback<List<String>>() {
            @Override
            public void onResponse(@NonNull Call<List<String>> call,@NonNull Response<List<String>> response) {
                if(response.isSuccessful() && response.body() != null){
                    roomNameStrListFromServer.addAll(response.body());

                    getInitViewItemDataObjectList(roomNameStrListFromServer);
                    roomAdapter = new ListViewItemCheckboxBaseAdapter(EditRoomActivity.this, initItemList);
                    roomAdapter.notifyDataSetChanged();
                    roomListView.setAdapter(roomAdapter);

                    roomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                            Object itemObject = parent.getAdapter().getItem(position); // get user selected item
                            final ListViewItemDataObject itemDataObject = (ListViewItemDataObject)itemObject;// convert selected item to dataObject
                            CheckBox itemCheckbox = view.findViewById(R.id.checkBoxTabItem);//get the checkbox

                            if(itemDataObject.isChecked()){
                                itemCheckbox.setChecked(false);
                                itemDataObject.setChecked(false);
                                initItemList.get(position).setChecked(false);
                            } else {
                                itemCheckbox.setChecked(true);
                                itemDataObject.setChecked(true);
                                initItemList.get(position).setChecked(true);
                            }
                        }
                    });
                } else {
                    Log.e("Error Code", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<String>> call,@NonNull Throwable t) {
                Log.d(DEBUG, String.valueOf(t));
            }
        });

        Button deleteRoomBtn = findViewById(R.id.tabRoomDeleteButton);
        deleteRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = initItemList.size();
                List<String> namesToDelete = new ArrayList<>();
                int isCheckedCount = 0;

                for(int i = 0; i < size; i++){
                    ListViewItemDataObject roomNameCheckBox = initItemList.get(i);
                    if(roomNameCheckBox.isChecked()){
                        isCheckedCount++;
                        String name = roomNameCheckBox.getItemText();
                        namesToDelete.add(name);

                        if(name.equals(roomNameStrListFromServer.get(i))){
                            roomNameStrListFromServer.remove(i);
                        }

                        initItemList.remove(i);
                        i--;
                        size = initItemList.size();
                    }
                }

                if(isCheckedCount == 0){
                    Toast.makeText(getApplicationContext(),
                            "No room is selected", Toast.LENGTH_SHORT).show();
                } else {
                    final AlertDialog alertDialog = new AlertDialog.Builder(EditRoomActivity.this).create();
                    alertDialog.setTitle("Warning Message");
                    alertDialog.setMessage("Are you sure to remove selected items?");
                    alertDialog.setIcon(R.mipmap.warning);

                    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            // update name list for display
                            updateRoomList(roomNameStrListFromServer);

                            // get the communicator to delete rooms in db
                            communicator.deleteRoomName(GroupId, namesToDelete, new Callback<Void>() {
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
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            }
        });

        Button addRoomBtn = findViewById(R.id.tabRoomAddButton);
        addRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.setDisplayedChild(1);
            }
        });

        // adding room content view
        roomListEditText = new ArrayList<EditText>();
        tableContainer = findViewById(R.id.roomTabTableContainer);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        newRowView = inflater.inflate(R.layout.new_edit_text, tableContainer, false);
        tableContainer.addView(newRowView);

        final EditText initNewRoom = newRowView.findViewById(R.id.newEditText);
        initNewRoom.requestFocus();

        Button addNewRoomBtn = findViewById(R.id.addRoomButtonOnRoomTab);
        addNewRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add the idx[0] to the list
                roomListEditText.add(initNewRoom);
                if(roomListEditText.size() < 1){ // list is empty
                    if(roomListEditText.get(0).getText().toString().equals("")){ // check initial box, if empty warn user
                        Toast.makeText(getApplicationContext(),
                                "Please enter a room name before adding another box", Toast.LENGTH_SHORT).show();
                    }
                    else { // if  not empty -- allow new box to user -- add new box to container.
                        tableContainer.addView(nextRowView);
                        EditText nextRoom = nextRowView.findViewById(R.id.newEditText);
                        nextRoom.requestFocus();
                        roomListEditText.add(nextRoom);
                    }
                }
                else { // check if next boxes is empty
                    if(roomListEditText.get(roomListEditText.size() - 1).getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),
                                "Please enter a room name before adding another box", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        roomListEditText.add(roomListEditText.get(roomListEditText.size() - 1));

                        // allow new box to user -- add new box to container.
                        tableContainer.addView(nextRowView);
                        EditText nextRoom = nextRowView.findViewById(R.id.newEditText);
                        nextRoom.requestFocus();
                        roomListEditText.add(nextRoom);
                    }
                }
            }
        });

        ImageButton homeBtn = findViewById(R.id.editRoomToAdminRoom);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditRoomActivity.this, AdminRoomActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        Button saveNewRoomsBtn = findViewById(R.id.saveNewRoomButtonOnRoomTab);
        saveNewRoomsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> newRoomNamesToSave = new ArrayList<>();
                roomListEditText.add(initNewRoom);
                if(roomListEditText.size() == 0){
                    Toast.makeText(getApplicationContext(),
                            "You have no room name to save", Toast.LENGTH_SHORT).show();
                }
                else if (roomListEditText.size() == 1){
                    // check the first box -- warn user if empty -- case: only 1 box and is empty
                    if(roomListEditText.get(0).getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(),
                                "Please enter a room name to save your new room list", Toast.LENGTH_SHORT).show();
                    }
                    else { // case: only 1 box and is NOT empty
                        for(int i = 0; i < roomListEditText.size(); i++){
                            String roomName = roomListEditText.get(i).getText().toString();
                            newRoomNamesToSave.add(roomName);
                        }

                        communicator.postAddRoomName(newRoomNamesToSave, GroupId, new Callback<Void>() {
                            @Override
                            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                                if(response.isSuccessful()){
                                    Log.i(TAG2, "Room addition process successful");
                                } else {
                                    Log.e("Error Code", String.valueOf(response.code()));
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                                Log.d(DEBUG, String.valueOf(t));
                            }
                        });
                        // clear boxes except 1
                        if(roomListEditText.size() > 1){
                            for(int i = 0; i < roomListEditText.size() - 1; i++){
                                tableContainer.removeView(roomListEditText.get(i));
                                roomListEditText.get(i).setText("");
                            }
                        }

                        //updated list
                        roomNameStrListFromServer.addAll(newRoomNamesToSave);
                        updateRoomList(roomNameStrListFromServer);

                        Toast.makeText(getApplicationContext(),
                                "New room list saved", Toast.LENGTH_SHORT).show();
                        viewFlipper.setDisplayedChild(0);
                    }
                }
                else {
                    // case: more than 1 box -- must go through non-empty validation
                    int emptyBoxCount = roomListEditText.size();

                    for(int i = 0; i < roomListEditText.size(); i++){
                        if(roomListEditText.get(i).getText().toString().isEmpty()){
                            // empty box count stays
                        } else {
                            emptyBoxCount--;
                        }
                    }

                    if(emptyBoxCount != 0){
                        Toast.makeText(getApplicationContext(),
                                "There are empty box(es) in your list. Please fill in the box(es) to continue.", Toast.LENGTH_SHORT).show();
                    }
                    else { // case: all boxes not empty
                        for(int i = 0; i < roomListEditText.size(); i++){
                            String roomName = roomListEditText.get(i).getText().toString();
                            newRoomNamesToSave.add(roomName);
                        }

                        // clear boxes except 1
                        if(roomListEditText.size() > 1){
                            for(int i = 0; i < roomListEditText.size() - 1; i++){
                                tableContainer.removeView(roomListEditText.get(i));
                            }
                        }

                        //updated list
                        roomNameStrListFromServer.addAll(newRoomNamesToSave);
                        updateRoomList(roomNameStrListFromServer);

                        communicator.postAddRoomName(newRoomNamesToSave, GroupId, new Callback<Void>() {
                            @Override
                            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                                if(response.isSuccessful()){
                                    Log.i(TAG2, "Room addition process successful");
                                } else {
                                    Log.e("Error Code", String.valueOf(response.code()));
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                                Log.d(DEBUG, String.valueOf(t));
                            }
                        });
                        viewFlipper.setDisplayedChild(0);
                    }
                }
            }
        });

        Button cancelAddingRoomBtn = findViewById(R.id.cancelRoomButtonOnRoomTab);
        cancelAddingRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // clear boxes except 1
                if(roomListEditText.size() > 1){
                    for(int i = 0; i < roomListEditText.size() - 1; i++){
                        tableContainer.removeView(roomListEditText.get(i));
                    }
                }

                viewFlipper.setDisplayedChild(0);
            }
        });
    }

    private void updateRoomList(ArrayList<String> newList){
        initItemList.clear();
        int length = newList.size();

        for(int i = 0; i < length; i++){
            ListViewItemDataObject singleRowObject = new ListViewItemDataObject();

            singleRowObject.setChecked(false);
            String roomName = newList.get(i);
            singleRowObject.setItemText(roomName);

            initItemList.add(singleRowObject);
        }
        roomAdapter.notifyDataSetChanged();
    }

    private void getInitViewItemDataObjectList(ArrayList<String> inParam_roomNameList){
        initItemList = new ArrayList<ListViewItemDataObject>();
        int length = inParam_roomNameList.size();

        for(int i = 0; i < length; i++){
            ListViewItemDataObject singleRowObject = new ListViewItemDataObject();

            singleRowObject.setChecked(false);
            String roomName = inParam_roomNameList.get(i);
            singleRowObject.setItemText(roomName);

            initItemList.add(singleRowObject);
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        // clear boxes except 1
        if(roomListEditText.size() > 1){
            for(int i = 0; i < roomListEditText.size() - 1; i++){
                tableContainer.removeView(roomListEditText.get(i));
            }
        }
        startActivity(new Intent(EditRoomActivity.this, HomePropertyTabsActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
