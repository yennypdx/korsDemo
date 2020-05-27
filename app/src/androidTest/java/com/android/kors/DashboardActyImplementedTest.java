package com.android.kors;

import android.view.View;

import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android.kors.activities.DashboardActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@SmallTest
@RunWith(AndroidJUnit4.class)
public class DashboardActyImplementedTest {
    @Rule
    public ActivityTestRule<DashboardActivity> rule = new ActivityTestRule<>(DashboardActivity.class);

    @Test
    public void confirmToolbarIsPresent() throws Exception {
        DashboardActivity activity = rule.getActivity();
        View toolbarById = activity.findViewById(R.id.toolbar_dashboard);

        assertThat(toolbarById, notNullValue());
    }

    @Test
    public void confirmTopImageBoxIsPresent() throws Exception {
        DashboardActivity activity = rule.getActivity();
        View topImageBoxById = activity.findViewById(R.id.top_banner);

        assertThat(topImageBoxById, notNullValue());
    }

    @Test
    public void confirmCalendarButtonIsPresent() throws Exception {
        DashboardActivity activity = rule.getActivity();
        View calendarBtnById = activity.findViewById(R.id.calendarButton);

        assertThat(calendarBtnById, notNullValue());
    }

    @Test
    public void confirmActiveAsgButtonIsPresent() throws Exception {
        DashboardActivity activity = rule.getActivity();
        View activeAsgBtnById = activity.findViewById(R.id.activeChoresButton);

        assertThat(activeAsgBtnById, notNullValue());
    }

    @Test
    public void confirmFamilyMemberButtonIsPresent() throws Exception {
        DashboardActivity activity = rule.getActivity();
        View familyMemberBtnById = activity.findViewById(R.id.calendarButton);

        assertThat(familyMemberBtnById, notNullValue());
    }

    @Test
    public void confirmSettingsButtonIsPresent() throws Exception {
        DashboardActivity activity = rule.getActivity();
        View settingsBtnById = activity.findViewById(R.id.settingsButton);

        assertThat(settingsBtnById, notNullValue());
    }

    @Test
    public void confirmAdminRoomButtonIsPresent() throws Exception {
        DashboardActivity activity = rule.getActivity();
        View adminRoomBtnById = activity.findViewById(R.id.adminRoomButton);

        assertThat(adminRoomBtnById, notNullValue());
    }
}
