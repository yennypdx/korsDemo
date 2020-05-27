package com.android.kors.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.android.kors.R;

public class EnlargedImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlarged_image);

        final int AssignmentId = getIntent().getIntExtra("AssignmentKey", 0);
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        int width = bmp.getWidth() * 5;
        int height = bmp.getHeight() * 5;
        Bitmap outputImage = Bitmap.createScaledBitmap(bmp, width, height, false);

        ImageView imageBox = findViewById(R.id.photoEnlargement);
        imageBox.setImageBitmap(outputImage);
        imageBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(getApplicationContext(), DetailCompletedAssignmentActivity.class);
                intent.putExtra("AssignmentKey", AssignmentId);
                startActivity(intent);
                finish();
                return false;
            }
        });
    }
}
