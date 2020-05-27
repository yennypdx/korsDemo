package com.android.kors.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.android.kors.helperClasses.Communicator;
import com.android.kors.helperClasses.CustomOnItemSelectedListener;
import com.android.kors.helperClasses.DbHelper;
import com.android.kors.models.Assignment;
import com.android.kors.models.Room;
import com.android.kors.models.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.kors.R;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateNewTaskActivity extends AppCompatActivity{
    private static final String DEBUG = "Debug Value";
    SharedPreferences sharedPreferences;
    public static final String currentUserPreference = "korsPreference";
    Communicator communicator = new Communicator();
    final Calendar calendarOne = Calendar.getInstance();
    final Calendar calendarTwo = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
    SimpleDateFormat stf = new SimpleDateFormat("hh:mm aa", Locale.US);

    List<String> roomNameList = new ArrayList<>();
    List<String> taskNameList = new ArrayList<>();
    List<String> userNameList = new ArrayList<>();
    TextView createdDate;
    TextView createdTime;
    TextView completionDate;
    TextView completionTime;
    EditText message;
    String position;
    int inputRoomId = 0;
    int inputTaskId = 0;
    int assigneeId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_task);
        Toolbar toolbar = findViewById(R.id.new_task_toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(currentUserPreference, Context.MODE_PRIVATE);
        DbHelper db = new DbHelper(getApplicationContext());
        final int groupId = db.getCurrentGroupId();
        final String userEmail = sharedPreferences.getString("emailKey", null);
        long date = System.currentTimeMillis();
        final String currentInitDate = sdf.format(date);
        String currentInitTime = stf.format(date);

        createdDate = findViewById(R.id.created_date_input);
        createdDate.setText(currentInitDate);
        createdTime = findViewById(R.id.created_time_input);
        createdTime.setText(currentInitTime);
        completionDate = findViewById(R.id.completion_date_input);
        completionDate.setText(currentInitDate);
        completionTime = findViewById(R.id.completion_time_input);
        completionTime.setText(currentInitTime);
        message = findViewById(R.id.instruction_input);
        message.requestFocus();

        final Spinner roomSpinner = findViewById(R.id.room_name_list_spinner);
        roomSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        final Spinner taskSpinner = findViewById(R.id.task_name_list_spinner);
        taskSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        final Spinner userListSpinner = findViewById(R.id.children_name_list_spinner);
        userListSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        // feeding the ROOMS SPINNER - get room name list from server
        communicator.getRoomNameListByGroupId(groupId, new Callback<List<String>>() {
            @Override
            public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    roomNameList = response.body();
                    ArrayAdapter<String> adapterRoom = new ArrayAdapter<String>(
                            getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, roomNameList);
                    adapterRoom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    roomSpinner.setAdapter(adapterRoom);

                    // feeding the TASKS SPINNER - get task name list from server
                    communicator.getTaskNameListByGroupId(groupId, new Callback<List<String>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                            if(response.isSuccessful() && response.body() != null) {
                                taskNameList = response.body();
                                ArrayAdapter<String> adapterTask = new ArrayAdapter<String>(
                                        getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, taskNameList);
                                adapterTask.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                taskSpinner.setAdapter(adapterTask);

                            } else {
                                Log.e("Error Code", String.valueOf(response.code()));
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
                            Log.d(DEBUG, String.valueOf(t));
                        }
                    });

                } else {
                    Log.e("Error Code", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
                Log.d(DEBUG, String.valueOf(t));
            }
        });

        // CREATED and COMPLETION DATE
        final DatePickerDialog.OnDateSetListener dateStart = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendarOne.set(Calendar.YEAR, year);
                calendarOne.set(Calendar.MONTH, month);
                calendarOne.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                createdDate.setText(sdf.format(calendarOne.getTime()));
            }
        };

        final DatePickerDialog.OnDateSetListener dateEnd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
                calendarTwo.set(Calendar.YEAR, year);
                calendarTwo.set(Calendar.MONTH, month);
                calendarTwo.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                completionDate.setText(sdf.format(calendarTwo.getTime()));
            }
        };

        completionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateNewTaskActivity.this, dateEnd,
                        calendarTwo.get(Calendar.YEAR),
                        calendarTwo.get(Calendar.MONTH),
                        calendarTwo.get(Calendar.DAY_OF_MONTH)).show();
                completionDate.setText(sdf.format(calendarTwo.getTime()));
            }
        });

        // CREATED and COMPLETION TIME
        final TimePickerDialog.OnTimeSetListener timeStart = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendarOne.set(Calendar.HOUR, hourOfDay);
                calendarOne.set(Calendar.MINUTE, minute);

                String meridian = "AM";
                if(hourOfDay > 11){
                    meridian = "PM";
                }

                int hour_of_12format;
                if(hourOfDay > 11){
                    hour_of_12format = hourOfDay - 12;
                }else{
                    hour_of_12format = hourOfDay;
                }

                createdTime.setText(String.format("%02d:%02d %s", hour_of_12format, minute, meridian));
            }
        };

        final TimePickerDialog.OnTimeSetListener timeEnd = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendarTwo.set(Calendar.HOUR, hourOfDay);
                calendarTwo.set(Calendar.MINUTE, minute);

                String meridian = "AM";
                if(hourOfDay > 11){
                    meridian = "PM";
                }

                int hour_of_12format;
                if(hourOfDay > 11){
                    hour_of_12format = hourOfDay - 12;
                }else{
                    hour_of_12format = hourOfDay;
                }

                completionTime.setText(String.format("%02d:%02d %s", hour_of_12format, minute, meridian));
            }
        };

        completionTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(CreateNewTaskActivity.this,
                        timeEnd,
                        calendarTwo.get(Calendar.HOUR),
                        calendarTwo.get(Calendar.MINUTE),
                        false).show();

                String meridian = "AM";
                int hourOfDay = calendarTwo.get(Calendar.HOUR);
                int minute = calendarTwo.get(Calendar.MINUTE);

                if(hourOfDay > 11){
                    meridian = "PM";
                }

                int hour_of_12format;
                if(hourOfDay > 11){
                    hour_of_12format = hourOfDay - 12;
                }else{
                    hour_of_12format = hourOfDay;
                }
                completionTime.setText(String.format("%02d:%02d %s", hour_of_12format, minute, meridian));
            }
        });

        communicator.getUserPositionByEmail(userEmail, new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(response.isSuccessful() && response.body() != null) {
                    position = response.body();
                    if(position.matches("Admin")){
                        // feeding the ASSIGNEE SPINNER - get user name list from server - Ver1.0 ONE GROUP ONLY
                        communicator.getUserNameListVersionOnePointZero(new Callback<List<String>>() {
                            @Override
                            public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                                if(response.isSuccessful() && response.body() != null) {
                                    userNameList = response.body();
                                    ArrayAdapter<String> adapterUserNames = new ArrayAdapter<>(
                                            getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, userNameList);
                                    adapterUserNames.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    userListSpinner.setAdapter(adapterUserNames);

                                } else {
                                    Log.e("Error Code", String.valueOf(response.code()));
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
                                Log.d(DEBUG, String.valueOf(t));
                            }
                        });

                    } else{
                        // feeding the ASSIGNEE SPINNER with the current user ONLY
                        communicator.getOneUserNameByEmail(userEmail, new Callback<String>() {
                            @Override
                            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                                if(response.isSuccessful() && response.body() != null) {
                                    String currentUser = response.body();
                                    userNameList.add(currentUser);
                                    ArrayAdapter<String> adapterUserNames = new ArrayAdapter<>(
                                            getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, userNameList);
                                    adapterUserNames.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    userListSpinner.setAdapter(adapterUserNames);

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

                } else {
                    Log.e("Error Code", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.d(DEBUG, String.valueOf(t));
            }
        });

        FloatingActionButton saveFab = findViewById(R.id.save_new_task_fab);
        saveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String selectedRoom = roomSpinner.getSelectedItem().toString();
                final String selectedTask = taskSpinner.getSelectedItem().toString();
                final String selectedUser = userListSpinner.getSelectedItem().toString();
                final String inputMessage = message.getText().toString();
                // getting date and time
                final String inputDateCreated = createdDate.getText().toString();
                final String inputTimeCreated = createdTime.getText().toString();
                final String inputDateToComplete = completionDate.getText().toString();
                final String inputTimeToComplete = completionTime.getText().toString();

                int dueMonth = Integer.parseInt(completionDate.getText().toString().substring(0,2));
                int currMonth = Integer.parseInt(createdDate.getText().toString().substring(0, 2));
                int dueDate = Integer.parseInt(completionDate.getText().toString().substring(3,5));
                int currDate = Integer.parseInt(createdDate.getText().toString().substring(3, 5));

                if(dueMonth >= currMonth && dueDate >= currDate){
                    // retrieving Room ID by room name
                    communicator.getRoomIdByRoomName(selectedRoom, groupId, new Callback<Integer>() {
                        @Override
                        public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                            if(response.isSuccessful() && response.body() != null) {
                                inputRoomId = response.body();

                                // retrieving Task ID by task name
                                communicator.getTaskIdByTaskName(selectedTask, groupId, new Callback<Integer>() {
                                    @Override
                                    public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                                        if(response.isSuccessful() && response.body() != null) {
                                            inputTaskId = response.body();

                                            // retrieving ASSIGNEE ID by assignee name
                                            communicator.getUserIdByName(selectedUser, new Callback<Integer>() {
                                                @Override
                                                public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                                                    if(response.isSuccessful() && response.body() != null) {
                                                        assigneeId = response.body();
                                                        Assignment newAssignment;

                                                        // TODO: do validation here, make sure all objects are complete
                                                        if(inputRoomId == 0 || inputTaskId == 0 || assigneeId == 0
                                                        || inputDateCreated == null || inputTimeCreated == null
                                                        || inputDateToComplete == null || inputTimeToComplete == null){
                                                            Toast.makeText(CreateNewTaskActivity.this, "One or more of data is invalid", Toast.LENGTH_SHORT).show();
                                                        }
                                                        else {
                                                            newAssignment = new Assignment(
                                                                    inputRoomId,
                                                                    inputTaskId,
                                                                    assigneeId,
                                                                    "Available Unconfirmed",
                                                                    inputDateCreated,
                                                                    inputTimeCreated,
                                                                    inputDateToComplete,
                                                                    inputTimeToComplete,
                                                                    inputMessage);

                                                            communicator.postInsertAssignment(newAssignment, new Callback<Void>() {
                                                                @Override
                                                                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                                                                    if(response.isSuccessful()) {                                                                // do nothing
                                                                        // do nothing
                                                                    } else {
                                                                        Log.e("Error Code", String.valueOf(response.code()));
                                                                    }
                                                                }

                                                                @Override
                                                                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                                                                    Log.d(DEBUG, String.valueOf(t));
                                                                }
                                                            });
                                                        }

                                                    } else {
                                                        Log.e("Error Code", String.valueOf(response.code()));
                                                    }
                                                }

                                                @Override
                                                public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                                                    Log.d(DEBUG, String.valueOf(t));
                                                }
                                            });

                                        } else {
                                            Log.e("Error Code", String.valueOf(response.code()));
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                                        Log.d(DEBUG, String.valueOf(t));
                                    }
                                });

                            } else {
                                Log.e("Error Code", String.valueOf(response.code()));
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                            Log.d(DEBUG, String.valueOf(t));
                        }
                    });
                    startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                    Snackbar.make(view, "New Task Added", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                } else {
                    Toast.makeText(getApplicationContext(),"Due date is invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
