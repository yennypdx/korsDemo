package com.android.kors;

import android.view.View;

import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android.kors.activities.CreateNewGroupActivity;
import com.android.kors.activities.GetAdminPhoneActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@SmallTest
@RunWith(AndroidJUnit4.class)
public class GetAdminPhnActyImplementedTest {
    @Rule
    public ActivityTestRule<GetAdminPhoneActivity> rule  = new ActivityTestRule<>(GetAdminPhoneActivity.class);

    @Test
    public void confirmPhoneImageBoxIsPresent() throws Exception {
        GetAdminPhoneActivity activity = rule.getActivity();
        View phoneImageBoxById = activity.findViewById(R.id.phone_imageBox);

        assertThat(phoneImageBoxById, notNullValue());
    }

    @Test
    public void confirmGetPhoneTextLabelIsPresent() throws Exception {
        GetAdminPhoneActivity activity = rule.getActivity();
        View textLabelById = activity.findViewById(R.id.adminPhoneTextLabel);

        assertThat(textLabelById, notNullValue());
    }

    @Test
    public void confirmPhoneInputTextIsPresent() throws Exception {
        GetAdminPhoneActivity activity = rule.getActivity();
        View phoneInputEditTextById = activity.findViewById(R.id.adminPhone_input);

        assertThat(phoneInputEditTextById, notNullValue());
    }

    @Test
    public void confirmNextButtonIsPresent() throws Exception {
        GetAdminPhoneActivity activity = rule.getActivity();
        View nextButtonById = activity.findViewById(R.id.nextToGetGroupNameBtn);

        assertThat(nextButtonById, notNullValue());
    }
}
