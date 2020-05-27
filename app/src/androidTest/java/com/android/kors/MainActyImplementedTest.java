package com.android.kors;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.test.filters.MediumTest;
import androidx.test.filters.SmallTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android.kors.activities.WelcomeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import static org.junit.Assert.*;

@SmallTest
@RunWith(AndroidJUnit4.class)
public class MainActyImplementedTest {
    @Rule
    public ActivityTestRule<MainActivity> rule  = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void confirmKorsLogoImageBoxIsPresent() throws Exception {
        MainActivity activity = rule.getActivity();
        View viewLogoImgBoxById = activity.findViewById(R.id.main_logo);

        assertThat(viewLogoImgBoxById, notNullValue());
    }

    @Test
    public void confirmTitleLabelIsPresent() throws Exception {
        MainActivity activity = rule.getActivity();
        View viewTitleLabelById = activity.findViewById(R.id.title_Text);

        assertThat(viewTitleLabelById, notNullValue());
    }

    @Test
    public void confirmStatusLabelIsPresent() throws Exception {
        MainActivity activity = rule.getActivity();
        View viewStatusLabelById = activity.findViewById(R.id.status);

        assertThat(viewStatusLabelById, notNullValue());
    }

    @Test
    public void confirmDetailLabelIsPresent() throws Exception {
        MainActivity activity = rule.getActivity();
        View viewDetailLabelById = activity.findViewById(R.id.detail);

        assertThat(viewDetailLabelById, notNullValue());
    }

    @Test
    public void confirmSignInButtonIsPresent() throws Exception {
        MainActivity activity = rule.getActivity();
        View viewSignInButtonById = activity.findViewById(R.id.sign_in_button);

        assertThat(viewSignInButtonById, notNullValue());
    }
}
