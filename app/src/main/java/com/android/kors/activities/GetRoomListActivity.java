package com.android.kors.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import com.android.kors.R;
import com.android.kors.helperClasses.DbHelper;
import com.android.kors.helperClasses.SetWallpaper;
import com.android.kors.models.Room;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GetRoomListActivity extends AppCompatActivity {
    TableLayout rtContainer;
    View newRowView;
    View nextRowView;
    LayoutInflater inflater;
    List<EditText> roomList;
    SharedPreferences sharedPreferences;
    public static final String currentUserPreference = "korsPreference";
    private SetWallpaper sw = new SetWallpaper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_room_list);

        DbHelper db = new DbHelper(getApplicationContext());
        final int wallId = db.getCurrentWallpaperPreference();
        ViewGroup wallView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        sw.MyWall(wallId, wallView);

        roomList = new ArrayList<EditText>();
        sharedPreferences = getSharedPreferences(currentUserPreference, Context.MODE_PRIVATE);
        final String groupName = getIntent().getStringExtra("gnameKey");
        final String phoneNum = getIntent().getStringExtra("phoneKey");

        rtContainer = findViewById(R.id.roomsEditTextContainer);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        newRowView = inflater.inflate(R.layout.new_edit_text, null);
        rtContainer.addView(newRowView);
        final EditText initRoom = newRowView.findViewById(R.id.newEditText);
        initRoom.requestFocus();

        Button addRoomBtn = findViewById(R.id.addRoomButton);
        addRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(roomList.size() == 0){
                    if(initRoom.getText().toString().matches("")){
                        Toast.makeText(getApplicationContext(),
                                "Please fill the first text box", Toast.LENGTH_SHORT).show();
                    } else {
                        roomList.add(initRoom); // idx[0]
                        nextRowView = inflater.inflate(R.layout.new_edit_text, null);
                        rtContainer.addView(nextRowView);
                        EditText tempRoom = nextRowView.findViewById(R.id.newEditText);
                        tempRoom.requestFocus();
                        roomList.add(tempRoom); // idx[1]
                    }

                } else if (roomList.size() >= 2){
                    if(roomList.get(roomList.size()-1).getText().toString().matches("")){
                        Toast.makeText(getApplicationContext(),
                                "Please enter a room name before adding new field", Toast.LENGTH_SHORT).show();
                    } else {
                        nextRowView = inflater.inflate(R.layout.new_edit_text, null);
                        rtContainer.addView(nextRowView);
                        EditText tempRoom = nextRowView.findViewById(R.id.newEditText);
                        tempRoom.requestFocus();
                        roomList.add(tempRoom);
                    }
                }
            }
        });

        Button delRoomBtn = findViewById(R.id.deleteRoomButton);
        delRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*int lastIdx = roomList.size();
            roomList.remove(lastIdx-1);
            rtContainer.removeView(nextRowView);
            rtContainer.invalidate();*/
            }
        });

        Button nextToGetTaskListButton = findViewById(R.id.nextToGetTaskTypesButton);
        nextToGetTaskListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> rooms = new ArrayList<>();

                if(initRoom.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext(),
                            "Please fill the first text box", Toast.LENGTH_SHORT).show();
                }else{
                    // save all rooms in the array list
                    for(int i = 0; i < roomList.size(); i++){
                        String roomName = roomList.get(i).getText().toString();
                        rooms.add(roomName);
                    }

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Set<String> set = new HashSet<>(rooms);
                    editor.putStringSet("RoomNames", set);
                    editor.apply();

                    Intent intent = new Intent(GetRoomListActivity.this, GetTaskListActivity.class);
                    intent.putExtra("gnameKey", groupName);
                    intent.putExtra("phoneKey", phoneNum);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), GetGroupNameActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
