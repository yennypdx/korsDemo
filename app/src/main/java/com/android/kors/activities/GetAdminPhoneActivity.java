package com.android.kors.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.kors.R;
import com.android.kors.helperClasses.DbHelper;
import com.android.kors.helperClasses.SetWallpaper;

public class GetAdminPhoneActivity extends AppCompatActivity {
    EditText userPhoneBox;
    private SetWallpaper sw = new SetWallpaper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_admin_phone);

        DbHelper db = new DbHelper(getApplicationContext());
        final int wallId = db.getCurrentWallpaperPreference();
        ViewGroup wallView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        sw.MyWall(wallId, wallView);

        userPhoneBox = findViewById(R.id.adminPhone_input);
        userPhoneBox.requestFocus();

        Button nextToGetGroupName = findViewById(R.id.nextToGetGroupNameBtn);
        nextToGetGroupName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userPhone = userPhoneBox.getText().toString();
                Intent intent = new Intent(GetAdminPhoneActivity.this, GetGroupNameActivity.class);
                intent.putExtra("phoneKey", userPhone);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), CreateNewGroupActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
