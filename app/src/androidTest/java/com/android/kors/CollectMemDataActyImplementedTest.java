package com.android.kors;

import android.view.View;

import androidx.test.filters.MediumTest;
import androidx.test.filters.SmallTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android.kors.activities.CollectMemberDataActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import static org.junit.Assert.*;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class CollectMemDataActyImplementedTest {
    @Rule
    public ActivityTestRule<CollectMemberDataActivity> rule  = new ActivityTestRule<>(CollectMemberDataActivity.class);

    @Test
    public void confirmInfoTextLabelIsPresent() throws Exception {
        CollectMemberDataActivity activity = rule.getActivity();
        View topTextLabelById = activity.findViewById(R.id.availMemDataText);

        assertThat(topTextLabelById, notNullValue());
    }

    @Test
    public void confirmUserNameLabelIsPresent() throws Exception {
        CollectMemberDataActivity activity = rule.getActivity();
        View userNameLabelById = activity.findViewById(R.id.googleName);

        assertThat(userNameLabelById, notNullValue());
    }

    @Test
    public void confirmUserNameResultBoxIsPresent() throws Exception {
        CollectMemberDataActivity activity = rule.getActivity();
        View userNameInputBoxById = activity.findViewById(R.id.googleNameInput);

        assertThat(userNameInputBoxById, notNullValue());
    }

    @Test
    public void confirmEmailLabelIsPresent() throws Exception {
        CollectMemberDataActivity activity = rule.getActivity();
        View emailLabelById = activity.findViewById(R.id.googleEmail);

        assertThat(emailLabelById, notNullValue());
    }

    @Test
    public void confirmEmailResultBoxIsPresent() throws Exception {
        CollectMemberDataActivity activity = rule.getActivity();
        View emailInputBoxById = activity.findViewById(R.id.googleNameInput);

        assertThat(emailInputBoxById, notNullValue());
    }

    @Test
    public void confirmInfoTextLabel2IsPresent() throws Exception {
        CollectMemberDataActivity activity = rule.getActivity();
        View midTextLabelById = activity.findViewById(R.id.phoneMemberDataText);

        assertThat(midTextLabelById, notNullValue());
    }

    @Test
    public void confirmPhoneLabelIsPresent() throws Exception {
        CollectMemberDataActivity activity = rule.getActivity();
        View phoneLabelById = activity.findViewById(R.id.phoneDataText);

        assertThat(phoneLabelById, notNullValue());
    }

    @Test
    public void confirmPhoneResultBoxIsPresent() throws Exception {
        CollectMemberDataActivity activity = rule.getActivity();
        View phoneInputBoxById = activity.findViewById(R.id.phoneDataInput);

        assertThat(phoneInputBoxById, notNullValue());
    }

    @Test
    public void confirmInfoLabel3IsPresent() throws Exception {
        CollectMemberDataActivity activity = rule.getActivity();
        View lastTextLabelById = activity.findViewById(R.id.dataConfirmText);

        assertThat(lastTextLabelById, notNullValue());
    }

    @Test
    public void confirmNextBtnIsPresent() throws Exception {
        CollectMemberDataActivity activity = rule.getActivity();
        View nextBtnById = activity.findViewById(R.id.nextToGroupListFromScan);

        assertThat(nextBtnById, notNullValue());
    }
}
