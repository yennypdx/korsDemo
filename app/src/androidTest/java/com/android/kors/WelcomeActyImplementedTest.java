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
import androidx.test.rule.ServiceTestRule;
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
public class WelcomeActyImplementedTest {
    @Rule
    public ActivityTestRule<WelcomeActivity> rule  = new ActivityTestRule<>(WelcomeActivity.class);

    @Test
    public void confirmWelcomeImageIsPresent() throws Exception {
        WelcomeActivity activity = rule.getActivity();
        View welcomeImageBoxById = activity.findViewById(R.id.opening_img);

        assertThat(welcomeImageBoxById, notNullValue());
    }

    @Test
    public void confirmProfilePhotoBoxIsPresent() throws Exception {
        WelcomeActivity activity = rule.getActivity();
        View userPhotoBoxById = activity.findViewById(R.id.userProfilePicture);

        assertThat(userPhotoBoxById, notNullValue());
    }

    @Test
    public void confirmUserNameLabelIsPresent() throws Exception {
        WelcomeActivity activity = rule.getActivity();
        View userNameLabelById = activity.findViewById(R.id.userGoogleAccName);

        assertThat(userNameLabelById, notNullValue());
    }

    @Test
    public void confirmUserEmailLabelIsPresent() throws Exception {
        WelcomeActivity activity = rule.getActivity();
        View userEmailLabelById = activity.findViewById(R.id.userGoogleEmail);

        assertThat(userEmailLabelById, notNullValue());
    }

    @Test
    public void confirmNextButtonIsPresent() throws Exception {
        WelcomeActivity activity = rule.getActivity();
        View nextButtonById = activity.findViewById(R.id.nextInWelcomeButton);

        assertThat(nextButtonById, notNullValue());
    }

    @Test
    public void confirmSignOutButtonIsPresent() throws Exception {
        WelcomeActivity activity = rule.getActivity();
        View signOutButtonById = activity.findViewById(R.id.signOut);

        assertThat(signOutButtonById, notNullValue());
    }

    /*
    @Override
    public void onReceive(Context ctx, Intent i) {
        if(Objects.requireNonNull(i.getAction()).equalsIgnoreCase(Intent.ACTION_PICK_ACTIVITY)){
            WelcomeActivity activity = rule.getActivity();
            Intent intent = new Intent(Intent.ACTION_PICK_ACTIVITY);
            intent.setClass(activity, MainActivity.class);
            ctx.startActivity(intent);
        }
    }

    @Test
    public void testStartActivityFromWelcomeActySignOutButton() {
        Intent intent = new Intent(Intent.ACTION_PICK_ACTIVITY);

    }*/
}
