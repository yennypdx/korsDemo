package com.android.kors;

import android.view.View;

import androidx.test.filters.SmallTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android.kors.activities.GetGroupNameActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import static org.junit.Assert.*;

@SmallTest
@RunWith(AndroidJUnit4.class)
public class GetGroupNameActyImplementedTest {
    @Rule
    public ActivityTestRule<GetGroupNameActivity> rule  = new ActivityTestRule<>(GetGroupNameActivity.class);

    @Test
    public void confirmPhoneImageBoxIsPresent() throws Exception {
        GetGroupNameActivity activity = rule.getActivity();
        View groupImageBoxById = activity.findViewById(R.id.groupNameImgBox);

        assertThat(groupImageBoxById, notNullValue());
    }

    @Test
    public void confirmGetGroupTextLabelIsPresent() throws Exception {
        GetGroupNameActivity activity = rule.getActivity();
        View ggTextLabelById = activity.findViewById(R.id.getGroupTextLabel);

        assertThat(ggTextLabelById, notNullValue());
    }

    @Test
    public void confirmPhoneInputTextIsPresent() throws Exception {
        GetGroupNameActivity activity = rule.getActivity();
        View nameInputEditTextById = activity.findViewById(R.id.groupName_input);

        assertThat(nameInputEditTextById, notNullValue());
    }

    @Test
    public void confirmNextButtonIsPresent() throws Exception {
        GetGroupNameActivity activity = rule.getActivity();
        View nextButtonById = activity.findViewById(R.id.nextToGetRoomsButton);

        assertThat(nextButtonById, notNullValue());
    }
}
