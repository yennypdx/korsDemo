package com.android.kors.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.android.kors.helperClasses.Communicator;
import com.android.kors.helperClasses.TaskRowAdapter;
import com.android.kors.models.Assignment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.kors.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.listeners.OnMonthChangeListener;
import sun.bob.mcalendarview.vo.DateData;

public class CalendarActivity extends AppCompatActivity {
    private static final String DEBUG = "Debug Value";
    private static final String ERROR = "Error Value";
    Communicator communicator = new Communicator();
    List<Assignment> assignmentList = new ArrayList<>();
    ListView assignmentListView;
    TextView monthTitle;
    TaskRowAdapter adapter;
    MCalendarView calendarView;
    private String dateSpecified = "x";
    List<String> dateList = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assignmentListView = findViewById(R.id.dateBasedTaskListView);
        monthTitle = findViewById(R.id.month_calendar_title);
        calendarView = findViewById(R.id.calendar);

        long date = System.currentTimeMillis();
        final String currentInitDate = sdf.format(date);
        String currentMonth = currentInitDate.substring(0,2);
        String currentYear = currentInitDate.substring(6,10);
        int monthForDisplay = Integer.parseInt(currentMonth);
        String monthName = GetMonthName(monthForDisplay);
        monthTitle.setText(monthName);

        communicator.getMonthlyListOfDatesToMark(currentMonth, currentYear, new Callback<List<String>>() {
            @Override
            public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    dateList.addAll(response.body());
                    for (String date:dateList) {
                        int mo = Integer.parseInt(date.substring(0,2));
                        int dt = Integer.parseInt(date.substring(3,5));
                        int yr = Integer.parseInt(date.substring(6,10));
                        calendarView.setMarkedStyle(MarkStyle.DOT, Color.GRAY).markDate(yr, mo, dt);
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

        calendarView.setOnMonthChangeListener(new OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                String mo = String.valueOf(month);
                String yr = String.valueOf(year);
                String monthName = GetMonthName(month);
                monthTitle.setText(monthName);

                communicator.getMonthlyListOfDatesToMark(mo, yr, new Callback<List<String>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                        if(response.isSuccessful() && response.body() != null) {
                            dateList.addAll(response.body());
                            for (String date:dateList) {
                                int mo = Integer.parseInt(date.substring(0,2));
                                int dt = Integer.parseInt(date.substring(3,5));
                                int yr = Integer.parseInt(date.substring(6,10));
                                calendarView.setMarkedStyle(MarkStyle.DOT, Color.GRAY).markDate(yr, mo, dt);
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
        });

        calendarView.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                dateSpecified = String.format("%02d/%02d/%04d", date.getMonth(), date.getDay(), date.getYear());

                // get assignment list from a single date completed
                communicator.getAssignmentListByCompletionDate(dateSpecified, new Callback<List<Assignment>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Assignment>> call, @NonNull Response<List<Assignment>> response) {
                        if(response.isSuccessful() && response.body() != null) {
                            assignmentList = response.body();
                            adapter = new TaskRowAdapter(getApplicationContext(), assignmentList);
                            assignmentListView.setAdapter(adapter);

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
        });

        assignmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int assignmentKey = assignmentList.get(position).getId();
                Intent intent = new Intent(getApplicationContext(), DetailTaskActivity.class);
                intent.putExtra("AssignmentKey", assignmentKey);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        FloatingActionButton createNewFab = findViewById(R.id.create_new_fab);
        createNewFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreateNewTaskActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        FloatingActionButton toDashboardFab = findViewById(R.id.toDashboard_fab);
        toDashboardFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    private String GetMonthName(int currentMonth){
        String month = "default";
        Map<Integer, String> Months = new LinkedHashMap<>();
        Months.put(1, "<  JANUARY  >");
        Months.put(2, "<  FEBRUARY  >");
        Months.put(3, "<  MARCH  >");
        Months.put(4, "<  APRIL  >");
        Months.put(5, "<  MAY  >");
        Months.put(6, "<  JUNE  >");
        Months.put(7, "<  JULY  >");
        Months.put(8, "<  AUGUST  >");
        Months.put(9, "<  SEPTEMBER  >");
        Months.put(10, "<  OCTOBER  >");
        Months.put(11, "<  NOVEMBER  >");
        Months.put(12, "<  DECEMBER  >");

        if(Months.containsKey(currentMonth)){
            month = Months.get(currentMonth);
        }
        return month;
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
