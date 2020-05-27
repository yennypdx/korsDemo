package com.android.kors;

import android.view.View;

import androidx.test.filters.SmallTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android.kors.activities.CreateNewGroupActivity;

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
public class CreateNewGroupActyImplementedTest {
    @Rule
    public ActivityTestRule<CreateNewGroupActivity> rule  = new ActivityTestRule<>(CreateNewGroupActivity.class);

    @Test
    public void confirmTextLabel1IsPresent() throws Exception {
        CreateNewGroupActivity activity = rule.getActivity();
        View topTextLabel1ById = activity.findViewById(R.id.createGroupText1);

        assertThat(topTextLabel1ById, notNullValue());
    }

    @Test
    public void confirmTextLabel2IsPresent() throws Exception {
        CreateNewGroupActivity activity = rule.getActivity();
        View topTextLabel2ById = activity.findViewById(R.id.createGroupText2);

        assertThat(topTextLabel2ById, notNullValue());
    }

    @Test
    public void confirmCreateNewButtonIsPresent() throws Exception {
        CreateNewGroupActivity activity = rule.getActivity();
        View createGroupButtonById = activity.findViewById(R.id.createNew);

        assertThat(createGroupButtonById, notNullValue());
    }

    @Test
    public void confirmJoinFamilyButtonIsPresent() throws Exception {
        CreateNewGroupActivity activity = rule.getActivity();
        View joinFamilyButtonById = activity.findViewById(R.id.joinFamily);

        assertThat(joinFamilyButtonById, notNullValue());
    }
}
