package com.android.kors.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.kors.R;
import com.android.kors.helperClasses.Communicator;
import com.android.kors.helperClasses.MessagingService;
import com.android.kors.models.SubmissionUpdate;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompletionReqApprovalActivity extends AppCompatActivity {
    private static final int CAMERA_PERMISSION_CODE = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String DEBUG = "Debug Value";
    private Communicator communicator = new Communicator();
    private MessagingService ms = new MessagingService();
    SimpleDateFormat sdf;
    SimpleDateFormat stf;
    String currentDate;
    String currentTime;

    String currentPhotoPath;
    File photoFile;
    String imageStringForServer;

    Button sendButton;
    Button cancelButton;
    Button captureButton;
    ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_completion_approval);

        Intent getAsgId = getIntent();
        final int AssignmentId = getAsgId.getIntExtra("AssignmentKey", 0);
        sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        stf = new SimpleDateFormat("hh:mm aa", Locale.US);

        //initializing bg for imageView
        imageView = findViewById(R.id.cameraInputImgView);
        Drawable drawable = getDrawable(R.mipmap.camera_lens);
        Bitmap drawableBitmap = ((BitmapDrawable) drawable).getBitmap();
        Drawable newDrawableImg = new BitmapDrawable(getResources(),
                Bitmap.createScaledBitmap(drawableBitmap, 500, 500, true));
        Bitmap finDrawableBitmap = ((BitmapDrawable) newDrawableImg).getBitmap();
        imageView.setImageBitmap(finDrawableBitmap);

        captureButton = findViewById(R.id.btnCamera);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(CompletionReqApprovalActivity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CompletionReqApprovalActivity.this,
                            new String[] {
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            }, 0);

                } else {
                    // dispatch take picture intent
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        cancelButton = findViewById(R.id.btnCameraCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sendButton = findViewById(R.id.btnCameraSend);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long date = System.currentTimeMillis();
                currentDate = sdf.format(date);
                currentTime = stf.format(date);
                SubmissionUpdate updateItems = new SubmissionUpdate(AssignmentId, "Completed", currentDate, currentTime);

                // update status, submission date and time
                communicator.updateSubmissionDateTimeStatus(updateItems,  new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            //Toast.makeText(getApplicationContext(),"Submission success", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("Error Code", String.valueOf(response.code()));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        Log.d(DEBUG, String.valueOf(t));
                    }
                });

                communicator.uploadImageByAssignment(AssignmentId, imageStringForServer, new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        if (response.isSuccessful()) {
                            //Toast.makeText(getApplicationContext(),"Image uploaded", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("Error Code", String.valueOf(response.code()));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        Log.d(DEBUG, String.valueOf(t));
                    }
                });

                communicator.getAdminPhone(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            String adminPhone = response.body();

                            String messageComplete = "You have a new completion approval request. Please check the List of Approval Request inside the Admin Room. [kors.]";
                            ms.sendClientTextMessage(adminPhone, messageComplete);
                            startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                            finish();
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

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
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                captureButton.setEnabled(true);
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    // create the File where the photo should go on hardware
                    photoFile = null;
                    try {
                        photoFile = createImageFile();

                    } catch (IOException ex) {
                        Log.d(DEBUG, String.valueOf(ex));
                    }
                    // continue only if the File was successfully created
                    if (photoFile != null) {
                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                        StrictMode.setVmPolicy(builder.build());

                        cameraIntent.putExtra(MediaStore.ACTION_IMAGE_CAPTURE, Uri.fromFile(photoFile));
                        startActivityForResult(cameraIntent, CAMERA_PERMISSION_CODE);
                    }
                }
            }else{
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private File createImageFile() throws IOException{
        // create image file name
        SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy", Locale.US);
        long date = System.currentTimeMillis();
        String timeStamp = sdf.format(date);
        String imageFileName = "IMG_" + timeStamp;

        File storageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "KorsImages");
        if(!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        // save file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");

            // convert Bitmap to byte[] to string Base64
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imgBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] imageBytes = stream.toByteArray();
            imageStringForServer = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);

            imageView.setImageBitmap(imgBitmap);
        }

        /*
        try{
            if (resultCode == RESULT_OK) {
                // Bitmap imgBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse("file://"+currentPhotoPath));
                Bitmap imgBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(photoFile));
                int width = imgBitmap.getWidth();
                int height = imgBitmap.getHeight();

                // rotate bitmap horizontal to vertical
                float degrees = 90;
                Matrix matrix = new Matrix();
                matrix.setRotate(degrees);
                Bitmap displayBitmap = Bitmap.createBitmap(imgBitmap, 0,0, width, height, matrix, true);

                //Bundle extras = data.getExtras();
                //Bitmap imgBitmap = (Bitmap) extras.get("data");

                // convert Bitmap to byte[] for db on server
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imgBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                imageForServer = stream.toByteArray();

                imageView.setImageBitmap(displayBitmap);

            }else{
                return;
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }*/
    }
}
