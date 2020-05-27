package com.android.kors.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.kors.R;
import com.android.kors.helperClasses.Communicator;
import com.android.kors.helperClasses.DbHelper;
import com.android.kors.helperClasses.SetWallpaper;
import com.android.kors.models.User;

import java.io.File;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanGroupActivity extends AppCompatActivity {
    private static final int CAMERA_PERMISSION_CODE = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    Communicator communicator = new Communicator();
    private SetWallpaper sw = new SetWallpaper();

    Button scannerButton;
    Button toCollectMemberDataButton;
    public static TextView resultTextView;
    TextView resultHelperText;
    String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_group);

        DbHelper db = new DbHelper(getApplicationContext());
        final int wallId = db.getCurrentWallpaperPreference();
        ViewGroup wallView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        sw.MyWall(wallId, wallView);

        resultHelperText = findViewById(R.id.code_con_text);
        resultHelperText.setVisibility(View.GONE);
        resultTextView = findViewById(R.id.codeConfirmation);
        resultTextView.setVisibility(View.GONE);
        toCollectMemberDataButton = findViewById(R.id.nextToCollectMemberDataFromScan);
        toCollectMemberDataButton.setVisibility(View.GONE);

        scannerButton = findViewById(R.id.scannerButton);
        scannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ScanGroupActivity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ScanGroupActivity.this,
                            new String[] {
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            }, 0);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                }
                resultHelperText.setVisibility(View.VISIBLE);
                resultTextView.setVisibility(View.VISIBLE);
                toCollectMemberDataButton.setVisibility(View.VISIBLE);
            }
        });

        toCollectMemberDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupName = resultTextView.getText().toString();
                Intent intent = new Intent(getApplicationContext(), CollectMemberDataActivity.class);
                intent.putExtra("gnameKey", groupName);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(new Intent(ScanGroupActivity.this, ScanCamActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }else{
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
}
