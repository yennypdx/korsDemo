package com.android.kors;

import android.view.View;

import androidx.test.filters.SmallTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android.kors.activities.GroupListActivity;
import com.android.kors.models.Group;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import static org.junit.Assert.*;

public class GroupListActyImplementedTest {
    @Rule
    public ActivityTestRule<GroupListActivity> rule  = new ActivityTestRule<>(GroupListActivity.class);

    @Test
    public void confirmInfoTextLabelIsPresent() throws Exception {
        GroupListActivity activity = rule.getActivity();
        View topTextLabelById = activity.findViewById(R.id.glTextLabel);

        assertThat(topTextLabelById, notNullValue());
    }

    @Test
    public void confirmInitialGroupButtonIsPresent() throws Exception {
        GroupListActivity activity = rule.getActivity();
        View initGroupButtonById = activity.findViewById(R.id.initGroup);

        assertThat(initGroupButtonById, notNullValue());
    }

    @Test
    public void confirmCreateNewGroupButtonIsPresent() throws Exception {
        GroupListActivity activity = rule.getActivity();
        View createNewGroupButtonById = activity.findViewById(R.id.createNewInGroupList);

        assertThat(createNewGroupButtonById, notNullValue());
    }

    @Test
    public void confirmJoinFamButtonIsPresent() throws Exception {
        GroupListActivity activity = rule.getActivity();
        View joinFamButtonById = activity.findViewById(R.id.joinFamilyInGroupList);

        assertThat(joinFamButtonById, notNullValue());
    }
}
