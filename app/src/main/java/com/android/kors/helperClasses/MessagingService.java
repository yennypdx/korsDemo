package com.android.kors.helperClasses;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagingService {
    private static final String TAG = "TWILIO TEXT-EMAIL";
    private Communicator communicator = new Communicator();
    private static final String DEBUG = "Debug Value";

    public void sendClientTextMessage(String phoneNumber, String message){
        communicator.postTextMessage(phoneNumber, message, new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Text message is sent successfully");
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

    public void sendAdminClientEmailMessage(final String message){
        communicator.getAdminEmailListFromServer(new Callback<List<String>>() {
            @Override
            public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<String> emails = response.body();
                    communicator.postEmailToAdmins(emails, message, new Callback<Void>() {
                        @Override
                        public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                            if (response.isSuccessful()) {
                                Log.i(TAG, "Email is sent successfully");
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
            public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
                Log.d(DEBUG, String.valueOf(t));
            }
        });
    }

    public void sendMemberClientEmailMessage(String email, String message){
        communicator.postEmailToMember(email, message, new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Email is sent successfully");
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

}
