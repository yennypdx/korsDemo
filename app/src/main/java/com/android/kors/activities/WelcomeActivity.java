package com.android.kors.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.kors.MainActivity;
import com.android.kors.R;
import com.android.kors.helperClasses.Communicator;
import com.android.kors.helperClasses.DbHelper;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomeActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public static final String currentUserPreference = "korsPreference";
    public static final String currentUserEmail = "emailKey";
    public static final String currentUserName = "nameKey";
    private static final String DEBUG = "Debug Value";
    Communicator communicator = new Communicator();
    private DbHelper db;
    GoogleSignInClient mGoogleSignInClient;
    ImageView profileImage;
    TextView userNameTexView;
    TextView userEmailTextView;
    Button signOutButton;
    Button nextButton;
    String userName;
    String userEmail;
    boolean userAvail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        db = new DbHelper(getApplicationContext());
        long dbItemCount = db.getDbItemCount();
        if(dbItemCount == 0){
            db.insertInitialSettingPreference(1, 2);
        }

        sharedPreferences = getSharedPreferences(currentUserPreference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();

        profileImage = findViewById(R.id.userProfilePicture);
        userNameTexView = findViewById(R.id.userGoogleAccName);
        userEmailTextView = findViewById(R.id.userGoogleEmail);

        // Configure sign-in to request basic profile which are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(WelcomeActivity.this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(WelcomeActivity.this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            userName = personName;
            String personEmail = acct.getEmail();
            userEmail = personEmail;
            Uri personPhoto = acct.getPhotoUrl();

            // pushed current user into SharedPreferences
            editor.putString(currentUserEmail, userEmail);
            editor.putString(currentUserName, userName);
            editor.apply();

            userNameTexView.setText(personName);
            userEmailTextView.setText(personEmail);
            Glide.with(this).load(personPhoto).into(profileImage);
        }

        userAvail = false;
        nextButton = findViewById(R.id.nextInWelcomeButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                communicator.getIsUserExist(userEmail, new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        if(response.isSuccessful()) {
                            if (response.body().equals("")) {
                                startActivity(new Intent(WelcomeActivity.this, CreateNewGroupActivity.class));
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                            } else { // user already registered in the db
                                communicator.getUserIdByEmail(userEmail, new Callback<Integer>() {
                                    @Override
                                    public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                                        if(response.isSuccessful() && response.body() != null) {
                                            int UserId = response.body();
                                            db.insertLocalUserId(UserId);

                                            startActivity(new Intent(WelcomeActivity.this, GroupListActivity.class));
                                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                                        } else {
                                            Log.e("Error Code", String.valueOf(response.code()));
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                                        Log.d(DEBUG, String.valueOf(t));
                                    }
                                });
                            }
                        } else {
                            Log.e("Error Code", String.valueOf(response.code()));
                            Log.e("Error Body", response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        Log.d(DEBUG, String.valueOf(t));
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                });
            }
        });

        signOutButton = findViewById(R.id.signOut);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(WelcomeActivity.this,"Successfully signed out",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                        finish();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                });
    }
}
