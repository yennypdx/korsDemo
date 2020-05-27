package com.android.kors;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.filters.LargeTest;
import androidx.test.filters.MediumTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android.kors.activities.EditRoomActivity;
import com.android.kors.activities.EditTaskActivity;
import com.android.kors.activities.HomePropertyTabsActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

// Documentation source: GoogleGit TabHostTest.Java >> blob: 5a225a1beee9f3e606a8517b11864541a81fe36c

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ModifyHomeInfoActyImplementedTest {
    private static final String ROOM_TAB = "Edit Room";
    private static final String TASK_TAB = "Edit Task";
    private static final int TAB_HOST_ID = android.R.id.tabhost;

    private Instrumentation mInstrumentation;
    private HomePropertyTabsActivity mActivity;

    @Rule
    public ActivityTestRule<HomePropertyTabsActivity> rule =
            new ActivityTestRule<>(HomePropertyTabsActivity.class);

    @Before
    public void setup() {
        mInstrumentation = InstrumentationRegistry.getInstrumentation();
        mActivity = rule.getActivity();
    }

    @Test
    public void testConstructor() throws Throwable {
        new TabHost(mActivity);

        new TabHost(mActivity, null);
    }

    @Test
    public void testNewTabSpecRoom() throws Throwable {
        TabHost tabHost = new TabHost(mActivity);

        assertNotNull(tabHost.newTabSpec(ROOM_TAB));
    }

    @Test
    public void testNewTabSpecTask() throws Throwable {
        TabHost tabHost = new TabHost(mActivity);

        assertNotNull(tabHost.newTabSpec(TASK_TAB));
    }

    // Check points:
    // 1. the tabWidget view and tabContent view associated with tabHost are created.
    // 2. no exception occurs when doing normal operation after setup().
    @Test
    public void testSetupRoomTab() throws Throwable {
        final Intent launchIntent = new Intent(Intent.ACTION_MAIN);
        launchIntent.setClassName("com.android.kors", HomePropertyTabsActivity.class.getName());
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        final Activity activity = mInstrumentation.startActivitySync(launchIntent);
        mInstrumentation.waitForIdleSync();

        rule.runOnUiThread(() -> {
            activity.setContentView(R.layout.activity_home_property_tabs);

            TabHost tabHost = (TabHost) activity.findViewById(TAB_HOST_ID);
            assertNull(tabHost.getTabWidget());
            assertNull(tabHost.getTabContentView());
            tabHost.setup();
            assertNotNull(tabHost.getTabWidget());
            assertNotNull(tabHost.getTabContentView());

            TabHost.TabSpec tabSpec = tabHost.newTabSpec(ROOM_TAB);
            tabSpec.setIndicator(ROOM_TAB);
            tabSpec.setContent(new TestTabContentFactoryList());

            tabHost.addTab(tabSpec);
            tabHost.setCurrentTab(0);
        });
        mInstrumentation.waitForIdleSync();

        activity.finish();
    }

    @Test
    public void testSetupTaskTab() throws Throwable {
        final Intent launchIntent = new Intent(Intent.ACTION_MAIN);
        launchIntent.setClassName("com.android.kors", HomePropertyTabsActivity.class.getName());
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        final Activity activity = mInstrumentation.startActivitySync(launchIntent);
        mInstrumentation.waitForIdleSync();

        rule.runOnUiThread(() -> {
            activity.setContentView(R.layout.activity_home_property_tabs);

            TabHost tabHost = (TabHost) activity.findViewById(TAB_HOST_ID);
            assertNull(tabHost.getTabWidget());
            assertNull(tabHost.getTabContentView());
            tabHost.setup();
            assertNotNull(tabHost.getTabWidget());
            assertNotNull(tabHost.getTabContentView());

            TabHost.TabSpec tabSpec = tabHost.newTabSpec(TASK_TAB);
            tabSpec.setIndicator(TASK_TAB);
            tabSpec.setContent(new TestTabContentFactoryList());

            tabHost.addTab(tabSpec);
            tabHost.setCurrentTab(1);
        });
        mInstrumentation.waitForIdleSync();

        activity.finish();
    }

    // Check points:
    // 1. the tabWidget view and tabContent view associated with tabHost are created.
    // 2. no exception occurs when uses TabSpec.setContent(android.content.Intent) after setup().



    // other tests
    @UiThreadTest
    @Test
    public void testAccessCurrentTab() {


    }

    @UiThreadTest
    @Test
    public void testGetCurrentTabView() {


    }

    @UiThreadTest
    @Test
    public void testGetCurrentView() {


    }

    @UiThreadTest
    @Test
    public void testGetTabContentView() {


    }

    // Check points:
    // 1. the specified callback should be invoked when the selected state of any of the items
    @UiThreadTest
    @Test
    public void testSetOnTabChangedListener() {

    }

    @UiThreadTest
    @Test
    public void testGetCurrentTabTag() {

    }

    // mock classes
    private class TestTabContentFactoryText implements TabHost.TabContentFactory {
        public View createTabContent(String tag) {
            final TextView tv = new TextView(mActivity);
            tv.setText(tag);
            return tv;
        }
    }

    private class TestTabContentFactoryList implements TabHost.TabContentFactory {
        public View createTabContent(String tag) {
            final ListView lv = new ListView(mActivity);
            return lv;
        }
    }
}
