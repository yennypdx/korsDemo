package com.android.kors;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.icu.text.IDNA;
import android.view.View;

import androidx.test.filters.MediumTest;
import androidx.test.filters.SmallTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.ServiceTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android.kors.activities.CreateNewGroupActivity;
import com.android.kors.activities.InformationActivity;
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
public class InformationActyImplementedTest {
    @Rule
    public ActivityTestRule<InformationActivity> rule  = new ActivityTestRule<>(InformationActivity.class);

    @Test
    public void confirmInfoTextLabel1IsPresent() throws Exception {
        InformationActivity activity = rule.getActivity();
        View textLabel1ById = activity.findViewById(R.id.infoText1);

        assertThat(textLabel1ById, notNullValue());
    }

    @Test
    public void confirmInfoTextLabel2IsPresent() throws Exception {
        InformationActivity activity = rule.getActivity();
        View textLabel2ById = activity.findViewById(R.id.infoText2);

        assertThat(textLabel2ById, notNullValue());
    }

    @Test
    public void confirmInfoTextLabel3IsPresent() throws Exception {
        InformationActivity activity = rule.getActivity();
        View textLabel3ById = activity.findViewById(R.id.infoText3);

        assertThat(textLabel3ById, notNullValue());
    }

    @Test
    public void confirmInfoTextLabel4IsPresent() throws Exception {
        InformationActivity activity = rule.getActivity();
        View textLabel4ById = activity.findViewById(R.id.infoText4);

        assertThat(textLabel4ById, notNullValue());
    }

    @Test
    public void confirmInfoTextLabel5IsPresent() throws Exception {
        InformationActivity activity = rule.getActivity();
        View textLabel5ById = activity.findViewById(R.id.infoText5);

        assertThat(textLabel5ById, notNullValue());
    }

    @Test
    public void confirmInfoTextLabel6IsPresent() throws Exception {
        InformationActivity activity = rule.getActivity();
        View textLabel6ById = activity.findViewById(R.id.infoText6);

        assertThat(textLabel6ById, notNullValue());
    }

    @Test
    public void confirmInfoTextLabel7IsPresent() throws Exception {
        InformationActivity activity = rule.getActivity();
        View textLabel7ById = activity.findViewById(R.id.infoText7);

        assertThat(textLabel7ById, notNullValue());
    }

}
