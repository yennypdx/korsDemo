package com.android.kors.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.kors.R;
import com.android.kors.helperClasses.Communicator;
import com.android.kors.helperClasses.CustomExpandableListAdapter;
import com.android.kors.helperClasses.CustomOnItemSelectedListener;
import com.android.kors.models.Assignment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActiveTaskListActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public static final String CURRENTUSERPREFERENCE = "korsPreference";
    private Communicator communicator = new Communicator();
    private static final String DEBUG = "Debug Value";
    private static final String ERROR = "Error Value";
    Calendar calendarFrom = Calendar.getInstance();
    Calendar calendarTo = Calendar.getInstance();
    SimpleDateFormat sdf;
    Button sortButton;
    TextView inputFromDateText;
    TextView inputToDateText;
    String fromDateGlobVar;
    String toDateGlobVar;

    ExpandableListView expandableListView;
    List<String> expandableStatusTitleList = new ArrayList<>();
    HashMap<String, List<Assignment>> expandableStatusChildren = new HashMap<>();
    List<String> expandableDueDateTitleList = new ArrayList<>();
    HashMap<String, List<Assignment>> expandableDueDateChildren = new HashMap<>();
    List<String> expandableNamesTitleList = new ArrayList<>();
    HashMap<String, List<Assignment>> expandableNamesChildren = new HashMap<>();
    ExpandableListAdapter expandableListAdapter;
    int userId = 0;
    int groupId = 0;
    int assignmentId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_task_list);
        expandableListView = findViewById(R.id.expandableLV);

        sharedPreferences = getSharedPreferences(CURRENTUSERPREFERENCE, Context.MODE_PRIVATE);
        groupId = sharedPreferences.getInt("gidKey", 0);

        // list of sorting option
        List<String> sortTypeList = new ArrayList<String>();
        sortTypeList.add("Status");
        sortTypeList.add("Due date");
        sortTypeList.add("Name");

        sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        long date = System.currentTimeMillis();
        String currentInitDate = sdf.format(date);

        inputFromDateText = findViewById(R.id.fromDateInput);
        inputFromDateText.setText(currentInitDate);
        inputToDateText = findViewById(R.id.toDateInput);
        inputToDateText.setText(currentInitDate);

        // FROM and TO interval date input
        final DatePickerDialog.OnDateSetListener fromDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendarFrom.set(Calendar.YEAR, year);
                calendarFrom.set(Calendar.MONTH, month);
                calendarFrom.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                inputFromDateText.setText(sdf.format(calendarFrom.getTime()));
            }
        };

        inputFromDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ActiveTaskListActivity.this, fromDate,
                        calendarFrom.get(Calendar.YEAR),
                        calendarFrom.get(Calendar.MONTH),
                        calendarFrom.get(Calendar.DAY_OF_MONTH)).show();
                inputFromDateText.setText(sdf.format(calendarFrom.getTime()));
                // fromDate interval
                fromDateGlobVar = sdf.format(calendarFrom.getTime());
            }
        });

        final DatePickerDialog.OnDateSetListener toDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendarTo.set(Calendar.YEAR, year);
                calendarTo.set(Calendar.MONTH, month);
                calendarTo.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                inputToDateText.setText(sdf.format(calendarTo.getTime()));
            }
        };

        inputToDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ActiveTaskListActivity.this, toDate,
                        calendarTo.get(Calendar.YEAR),
                        calendarTo.get(Calendar.MONTH),
                        calendarTo.get(Calendar.DAY_OF_MONTH)).show();
                inputToDateText.setText(sdf.format(calendarTo.getTime()));
                // ToDate interval
                toDateGlobVar = sdf.format(calendarTo.getTime());
            }
        });

        // feeding the sort type spinner
        final Spinner inputSortSpinner = findViewById(R.id.sortOptionSpinner);
        inputSortSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sortTypeList);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputSortSpinner.setAdapter(sortAdapter);

        // sort button
        sortButton = findViewById(R.id.sortButton);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDateGlobVar = sdf.format(calendarFrom.getTime());
                toDateGlobVar = sdf.format(calendarTo.getTime());
                String sortTypeSelected = String.valueOf(inputSortSpinner.getSelectedItem());

                switch(sortTypeSelected){
                    case "Due date":
                        sortByDueDates(getApplicationContext(), fromDateGlobVar, toDateGlobVar);
                        break;
                    case "Name":
                        sortByNames(getApplicationContext(), fromDateGlobVar, toDateGlobVar);
                        break;
                    default:
                        sortByStatus(getApplicationContext(), fromDateGlobVar, toDateGlobVar);
                        break;
                }
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + "List Expanded",
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + "List Collapsed",
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                TextView tempVal = v.findViewById(R.id.asgId_inRow);
                assignmentId = Integer.parseInt(tempVal.getText().toString());
                Intent intent = new Intent(getApplicationContext(), DetailTaskActivity.class);
                intent.putExtra("AssignmentKey", assignmentId);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return false;
            }
        });
    }

    // sorting by STATUS
    public void sortByStatus(Context context, String dStart, String dEnd){
        List<String> listStatus = new ArrayList<>();
        listStatus.add("Available Unconfirmed");
        listStatus.add("Available Confirmed");
        listStatus.add("Negotiating");
        listStatus.add("Completed");
        listStatus.add("Closed");

        for(int s = 0; s < listStatus.size(); s++){
            callCommunicatorStatus(listStatus.get(s), dStart, dEnd);
        }
    }

    private void callCommunicatorStatus(final String status, String dStart, String dEnd){
        communicator.getAssignmentListByStatusName(status, dStart, dEnd, new Callback<List<Assignment>>() {
            @Override
            public void onResponse(@NonNull Call<List<Assignment>> call, @NonNull Response<List<Assignment>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<Assignment> assignmentListBasedOnStatus = new ArrayList<>();
                    assignmentListBasedOnStatus.addAll(response.body());

                    expandableStatusChildren.put(status, assignmentListBasedOnStatus);
                    expandableStatusTitleList = new ArrayList<>(expandableStatusChildren.keySet());

                    expandableListAdapter = new CustomExpandableListAdapter(getApplicationContext(),
                            expandableStatusTitleList, expandableStatusChildren);
                    expandableListView.setAdapter(expandableListAdapter);

                } else {
                    Log.e(ERROR, String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Assignment>> call, @NonNull Throwable t) {
                Log.d(DEBUG, String.valueOf(t));
            }
        });
    }

    // sorting by DATES
    public void sortByDueDates(Context context, String dStart, final String dEnd){
        communicator.getListOfDatesWithinInterval(dStart, dEnd, new Callback<List<String>>() {
            @Override
            public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<String> listDates = response.body();

                    for(int dNum = 0; dNum < listDates.size(); dNum++){
                        final String dateStr = listDates.get(dNum);
                        callCommunicatorDates(dateStr);
                    }
                } else {
                    Log.e(ERROR, String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
                Log.d(DEBUG, String.valueOf(t));
            }
        });
    }

    private void callCommunicatorDates(final String date){
        // get assignment list from a single date completed
        communicator.getAssignmentListByCompletionDate(date, new Callback<List<Assignment>>() {
            @Override
            public void onResponse(@NonNull Call<List<Assignment>> call, @NonNull Response<List<Assignment>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<Assignment> assignmentListOnOneDateCompleted = new ArrayList<>();
                    assignmentListOnOneDateCompleted.addAll(response.body());

                    expandableDueDateChildren.put(date, assignmentListOnOneDateCompleted);
                    expandableDueDateTitleList = new ArrayList<>(expandableDueDateChildren.keySet());

                    ExpandableListAdapter expandableListAdapter = new CustomExpandableListAdapter(getApplicationContext(),
                            expandableDueDateTitleList, expandableDueDateChildren);
                    expandableListView.setAdapter(expandableListAdapter);
                } else {
                    Log.e(ERROR, String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Assignment>> call, @NonNull Throwable t) {
                Log.d(DEBUG, String.valueOf(t));
            }
        });
    }

    // sorting by NAMES
    public void sortByNames(Context context, final String dStart, final String dEnd){
        final HashMap<String, List<Assignment>> expandableDetail = new HashMap<String, List<Assignment>>();

        // get the list of user name
        communicator.getUserNameListVersionOnePointZero(new Callback<List<String>>() {
            @Override
            public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<String> userNames = response.body();
                    // get each of available item under each names
                    for(int nNum = 0; nNum < userNames.size(); nNum++){
                        final String name = userNames.get(nNum);
                        callCommunicatorGetUserIdByName(name, dStart, dEnd);
                    }
                } else {
                    Log.e(ERROR, String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
                Log.d(DEBUG, String.valueOf(t));
            }
        });
    }

    private void callCommunicatorGetUserIdByName(String paramName, String paramDateStart, String paramDateEnd){
        // get User ID from server to get the assignment list
        communicator.getUserIdByName(paramName, new Callback<Integer>() {
            @Override
            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                if(response.isSuccessful() && response.body() != null) {
                    userId = response.body();
                    callCommunicatorNames(paramName, userId, paramDateStart, paramDateEnd);
                } else {
                    Log.e(ERROR, String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                Log.d(DEBUG, String.valueOf(t));
            }
        });
    }

    private void callCommunicatorNames(final String name, int userId, String dStart, String dEnd){
        // get assignment list from a single user
        communicator.getAssignmentListByUserIdWithinInterval(userId, dStart, dEnd, new Callback<List<Assignment>>() {
            @Override
            public void onResponse(@NonNull Call<List<Assignment>> call, @NonNull Response<List<Assignment>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<Assignment> assignmentListBasedOnUser = new ArrayList<>();
                    assignmentListBasedOnUser.addAll(response.body());

                    expandableNamesChildren.put(name, assignmentListBasedOnUser);
                    expandableNamesTitleList = new ArrayList<>(expandableNamesChildren.keySet());

                    ExpandableListAdapter expandableListAdapter = new CustomExpandableListAdapter(getApplicationContext(),
                            expandableNamesTitleList, expandableNamesChildren);
                    expandableListView.setAdapter(expandableListAdapter);
                } else {
                    Log.e(ERROR, String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Assignment>> call, @NonNull Throwable t) {
                Log.d(DEBUG, String.valueOf(t));
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
