package com.android.kors;

import android.view.View;

import androidx.test.filters.MediumTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android.kors.activities.SettingsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class SettingsActyImplementedTest {
    @Rule
    public ActivityTestRule<SettingsActivity> rule  = new ActivityTestRule<>(SettingsActivity.class);

    @Test
    public void confirmNotifTextLabelIsPresent() throws Exception {
        SettingsActivity activity = rule.getActivity();
        View notifTextById = activity.findViewById(R.id.setting_messaging_instr);

        assertThat(notifTextById, notNullValue());
    }

    @Test
    public void confirmCheckBoxDefaultIsPresent() throws Exception {
        SettingsActivity activity = rule.getActivity();
        View checkBoxDefaultTextById = activity.findViewById(R.id.checkbox_default);

        assertThat(checkBoxDefaultTextById, notNullValue());
    }

    @Test
    public void confirmCheckBoxEmailIsPresent() throws Exception {
        SettingsActivity activity = rule.getActivity();
        View checkBoxEmailTextById = activity.findViewById(R.id.checkbox_email);

        assertThat(checkBoxEmailTextById, notNullValue());
    }

    @Test
    public void confirmCheckBoxNoneIsPresent() throws Exception {
        SettingsActivity activity = rule.getActivity();
        View checkBoxNoneTextById = activity.findViewById(R.id.checkbox_none);

        assertThat(checkBoxNoneTextById, notNullValue());
    }

    @Test
    public void confirmUpdateNotifButtonIsPresent() throws Exception {
        SettingsActivity activity = rule.getActivity();
        View updateNotifBtnById = activity.findViewById(R.id.setting_save_button);

        assertThat(updateNotifBtnById, notNullValue());
    }

    @Test
    public void confirmWallpaperTextLabelIsPresent() throws Exception {
        SettingsActivity activity = rule.getActivity();
        View wallpaperNotifTextLabelById = activity.findViewById(R.id.setting_wallpaper_instr);

        assertThat(wallpaperNotifTextLabelById, notNullValue());
    }

    @Test
    public void confirmWallpaperSpinnerIsPresent() throws Exception {
        SettingsActivity activity = rule.getActivity();
        View wallpaperSpinnerById = activity.findViewById(R.id.spinner_wallpaper);

        assertThat(wallpaperSpinnerById, notNullValue());
    }

    @Test
    public void confirmUpdateWallpaperButtonIsPresent() throws Exception {
        SettingsActivity activity = rule.getActivity();
        View updateWallpaperBtnById = activity.findViewById(R.id.wallpaper_setting_button);

        assertThat(updateWallpaperBtnById, notNullValue());
    }
}
