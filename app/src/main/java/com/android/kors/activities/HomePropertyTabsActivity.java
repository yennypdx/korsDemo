package com.android.kors.activities;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.kors.R;
import com.android.kors.helperClasses.DbHelper;

import java.util.ArrayList;

public class HomePropertyTabsActivity extends TabActivity {
    //tab names
    private static final String ROOM_SPEC = "Edit Room";
    private static final String TASK_SPEC = "Edit Task";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_property_tabs);

        DbHelper db = new DbHelper(getApplicationContext());
        TabHost tabHost = getTabHost();
        LayoutInflater inflater = LayoutInflater.from(this);

        // room tab
        TabHost.TabSpec roomSpec = tabHost.newTabSpec(ROOM_SPEC);
        // room tab icon
        roomSpec.setIndicator(createTabIndicator(inflater, tabHost, ROOM_SPEC, R.drawable.icon_room));
        roomSpec.setContent(new Intent(this, EditRoomActivity.class));
        tabHost.addTab(roomSpec);

        // task tab
        TabHost.TabSpec taskSpec = tabHost.newTabSpec(TASK_SPEC);
        taskSpec = tabHost.newTabSpec(TASK_SPEC);
        // task tab icon
        taskSpec.setIndicator(createTabIndicator(inflater, tabHost, TASK_SPEC, R.drawable.icon_task));
        taskSpec.setContent(new Intent(this, EditTaskActivity.class));
        tabHost.addTab(taskSpec);
    }

    public View createTabIndicator(LayoutInflater inflater, TabHost tabHost, String textResource, int iconResource) {
        View tabIndicator = inflater.inflate(R.layout.tab_indicator, tabHost.getTabWidget(), false);
        ((ImageView) tabIndicator.findViewById(R.id.tabIcon)).setImageResource(iconResource);
        ((TextView) tabIndicator.findViewById(R.id.tabTitle)).setText(textResource);

        return tabIndicator;
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), AdminRoomActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
