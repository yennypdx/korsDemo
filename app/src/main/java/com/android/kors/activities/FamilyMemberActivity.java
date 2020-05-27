package com.android.kors.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.android.kors.R;
import com.android.kors.helperClasses.Communicator;
import com.android.kors.helperClasses.DbHelper;
import com.android.kors.helperClasses.SetWallpaper;
import com.android.kors.models.User;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FamilyMemberActivity extends AppCompatActivity {
    private static final String DEBUG = "Debug Value";
    final static int QRcodeWidth = 500 ;
    final static int QRcodeLength = 500 ;
    ImageView imageBarcode;
    Bitmap bitmapResult;
    TextView groupNameText;
    TableLayout userContainer;
    LayoutInflater inflater;
    View newUserRow;

    Communicator communicator = new Communicator();
    private SetWallpaper sw = new SetWallpaper();
    private DbHelper db;
    long userCount = 0;
    List<User> familyMemberList = new ArrayList<>();
    String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_member);

        db = new DbHelper(getApplicationContext());
        final int wallId = db.getCurrentWallpaperPreference();
        ViewGroup wallView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        sw.MyWall(wallId, wallView);

        groupName = db.getCurrentGroupName();
        groupNameText = findViewById(R.id.fam_member_gn_display);
        groupNameText.setText(groupName);

        imageBarcode = findViewById(R.id.barcode_iv);
        userContainer = findViewById(R.id.userListContainer);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // get user count of the group from the server
        communicator.getTotalUserCount(new Callback<Long>() {
            @Override
            public void onResponse(@NonNull Call<Long> call, @NonNull Response<Long> response) {
                if(response.isSuccessful() && response.body() != null) {

                    userCount = response.body();

                    // get list of group member name and position from the server
                    communicator.getUserNameAndPositionList(new Callback<List<User>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                            if(response.isSuccessful() && response.body() != null) {
                                familyMemberList.addAll(response.body());

                                for(int i = 0; i < userCount; i++){
                                    newUserRow = inflater.inflate(R.layout.new_family_member, null);
                                    userContainer.addView(newUserRow);

                                    TextView memberName = newUserRow.findViewById(R.id.memberNameText);
                                    memberName.setText(familyMemberList.get(i).getName());
                                    TextView memberPosition = newUserRow.findViewById(R.id.memberPositionText);
                                    memberPosition.setText(familyMemberList.get(i).getPosition());
                                }
                            } else {
                                Log.e("Error Code", String.valueOf(response.code()));
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                            Log.d(DEBUG, String.valueOf(t));
                        }
                    });

                } else {
                Log.e("Error Code", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Long> call, @NonNull Throwable t) {
                Log.d(DEBUG, String.valueOf(t));
            }
        });

        // QR code generator, store to db, then display
        try {
            bitmapResult = TextToImageEncode(groupName);
            imageBarcode.setImageBitmap(bitmapResult);
            /*
            byte[] QrCode = getBytes(bitmapResult);
            communicator.postInsertGroupQrCode(groupName, QrCode, new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if(response.isSuccessful()) {
                        // do nothing
                    } else {
                        Log.e("Error Code", String.valueOf(response.code()));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    t.printStackTrace();
                }
            }); */

        } catch (WriterException e) {
            Log.d(DEBUG, String.valueOf(e));
        }
    }

    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix matrix;
        try {
            matrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.QR_CODE,
                    QRcodeWidth, QRcodeLength, null );

        } catch (IllegalArgumentException Illegalargumentexception) {
            return null;
        }
        int bitMatrixWidth = matrix.getWidth();
        int bitMatrixHeight = matrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = matrix.get(x, y) ?
                        getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    // convert from bitmap to byte array
    public byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        intent.putExtra("gname", groupName);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}
