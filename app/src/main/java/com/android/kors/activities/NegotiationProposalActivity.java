package com.android.kors.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.kors.R;
import com.android.kors.helperClasses.Communicator;
import com.android.kors.helperClasses.DbHelper;
import com.android.kors.helperClasses.Message;
import com.android.kors.helperClasses.MessagingService;
import com.android.kors.models.Negotiation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NegotiationProposalActivity extends AppCompatActivity {
    private static final String DEBUG = "Debug Value";
    Communicator communicator = new Communicator();
    private MessagingService ms = new MessagingService();
    final Calendar calendar = Calendar.getInstance();
    TextView proposedDate;
    TextView proposedTime;
    EditText proposedReason;
    SimpleDateFormat sdf;
    SimpleDateFormat stf;
    String finalProposedDate;
    String finalProposedTime;
    int AssignmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negotiation_proposal);

        DbHelper db = new DbHelper(getApplicationContext());
        AssignmentId = getIntent().getIntExtra("AssignmentKey",0);

        sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        stf = new SimpleDateFormat("hh:mm aa", Locale.US);
        long date = System.currentTimeMillis();
        String currentInitDate = sdf.format(date);
        String currentInitTime = stf.format(date);

        proposedDate = findViewById(R.id.Proposed_new_completionDate);
        proposedDate.setText(currentInitDate);
        proposedTime = findViewById(R.id.Proposed_new_completionTime);
        proposedTime.setText(currentInitTime);
        proposedReason = findViewById(R.id.Proposed_reason_Output);

        final DatePickerDialog.OnDateSetListener propDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                proposedDate.setText(sdf.format(calendar.getTime()));
            }
        };

        proposedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(NegotiationProposalActivity.this, propDate,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
                proposedDate.setText(sdf.format(calendar.getTime()));

            }
        });

        final TimePickerDialog.OnTimeSetListener timeStart = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

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
                proposedTime.setText(String.format("%02d:%02d %s", hour_of_12format, minute, meridian));
            }
        };

        proposedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(NegotiationProposalActivity.this,
                        timeStart,
                        calendar.get(Calendar.HOUR),
                        calendar.get(Calendar.MINUTE),
                        false).show();

                String meridian = "AM";
                int hourOfDay = calendar.get(Calendar.HOUR);
                int minute = calendar.get(Calendar.MINUTE);

                if(hourOfDay > 11){
                    meridian = "PM";
                }

                int hour_of_12format;
                if(hourOfDay > 11){
                    hour_of_12format = hourOfDay - 12;
                }else{
                    hour_of_12format = hourOfDay;
                }
                proposedTime.setText(String.format("%02d:%02d %s", hour_of_12format, minute, meridian));
            }
        });

        Button cancelProposalButton = findViewById(R.id.proposalCancelButton);
        cancelProposalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        final int NotifPref = db.getCurrentNotificationPreference();
        Button sendProposalButton = findViewById(R.id.proposalSendButton);
        sendProposalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalProposedDate = proposedDate.getText().toString();
                finalProposedTime = proposedTime.getText().toString();
                String reason = proposedReason.getText().toString();

                Negotiation negotiation = new Negotiation(
                        AssignmentId,
                        finalProposedDate,
                        finalProposedTime,
                        reason);

                communicator.postNegotiationData(negotiation, new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Negotiation sent", Toast.LENGTH_SHORT).show();

                            communicator.getAdminPhone(new Callback<String>() {
                                @Override
                                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        String adminPhone = response.body();
                                        String messageNegotiateToAdmin = Message.getIncomingNegotiationText();

                                        if(NotifPref == 2){
                                            ms.sendClientTextMessage(adminPhone, messageNegotiateToAdmin);
                                        } else if(NotifPref == 3){
                                            ms.sendAdminClientEmailMessage(messageNegotiateToAdmin);
                                        } else if(NotifPref == 4){
                                            ms.sendClientTextMessage(adminPhone, messageNegotiateToAdmin);
                                            ms.sendAdminClientEmailMessage(messageNegotiateToAdmin);
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
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        Log.d(DEBUG, String.valueOf(t));
                    }
                });
                startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }
}
