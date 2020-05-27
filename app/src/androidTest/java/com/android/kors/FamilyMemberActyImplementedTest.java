package com.android.kors;

import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.test.filters.MediumTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android.kors.activities.FamilyMemberActivity;
import com.android.kors.helperClasses.TaskRowAdapter;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class FamilyMemberActyImplementedTest {
    @Rule
    public ActivityTestRule<FamilyMemberActivity> rule = new ActivityTestRule<>(FamilyMemberActivity.class);

    @Test
    public void confirmGroupNameLabelIsPresent() throws Exception {
        FamilyMemberActivity activity = rule.getActivity();
        View groupNameLabelById = activity.findViewById(R.id.gn_text);

        assertThat(groupNameLabelById, notNullValue());
    }

    @Test
    public void confirmGroupNameOutputBoxIsPresent() throws Exception {
        FamilyMemberActivity activity = rule.getActivity();
        View groupNameOutputBoxById = activity.findViewById(R.id.fam_member_gn_display);

        assertThat(groupNameOutputBoxById, notNullValue());
    }

    @Test
    public void confirmGroupMemberLabelIsPresent() throws Exception {
        FamilyMemberActivity activity = rule.getActivity();
        View groupMemberLabelById = activity.findViewById(R.id.gm_text);

        assertThat(groupMemberLabelById, notNullValue());
    }

    @Test
    public void confirmGroupMemberListIsPresent() throws Exception {
        FamilyMemberActivity activity = rule.getActivity();
        View groupMemberListById = activity.findViewById(R.id.userListContainer);

        assertThat(groupMemberListById, notNullValue());
    }

    @Test
    public void confirmGroupQrLabelIsPresent() throws Exception {
        FamilyMemberActivity activity = rule.getActivity();
        View groupQrLabelById = activity.findViewById(R.id.code_display);

        assertThat(groupQrLabelById, notNullValue());
    }

    @Test
    public void confirmQrOutputBoxIsPresent() throws Exception {
        FamilyMemberActivity activity = rule.getActivity();
        View qrOutputBoxById = activity.findViewById(R.id.barcode_iv);

        assertThat(qrOutputBoxById, notNullValue());
    }
}
