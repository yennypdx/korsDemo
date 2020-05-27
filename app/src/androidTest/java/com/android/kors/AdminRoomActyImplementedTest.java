package com.android.kors;


import android.view.View;

import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android.kors.activities.AdminRoomActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@SmallTest
@RunWith(AndroidJUnit4.class)
public class AdminRoomActyImplementedTest {
    @Rule
    public ActivityTestRule<AdminRoomActivity> rule = new ActivityTestRule<>(AdminRoomActivity.class);

    @Test
    public void confirmApprovalReqListButtonIsPresent() throws Exception {
        AdminRoomActivity activity = rule.getActivity();
        View approvalReqListBtnById = activity.findViewById(R.id.approvalListBtn);

        assertThat(approvalReqListBtnById, notNullValue());
    }

    @Test
    public void confirmNegotiatedListButtonIsPresent() throws Exception {
        AdminRoomActivity activity = rule.getActivity();
        View negotiatedListBtnById = activity.findViewById(R.id.negotiateListBtn);

        assertThat(negotiatedListBtnById, notNullValue());
    }

    @Test
    public void confirmModifyHomeInfoButtonIsPresent() throws Exception {
        AdminRoomActivity activity = rule.getActivity();
        View modifyHomeInfoBtnById = activity.findViewById(R.id.modifyHomeBtn);

        assertThat(modifyHomeInfoBtnById, notNullValue());
    }

    @Test
    public void confirmModifyMemberPermissionButtonIsPresent() throws Exception {
        AdminRoomActivity activity = rule.getActivity();
        View modifyMemPermissionBtnById = activity.findViewById(R.id.modifyMemberBtn);

        assertThat(modifyMemPermissionBtnById, notNullValue());
    }

    @Test
    public void confirmRemoveFamilyMemberButtonIsPresent() throws Exception {
        AdminRoomActivity activity = rule.getActivity();
        View removeFamBtnById = activity.findViewById(R.id.removeMemberButton);

        assertThat(removeFamBtnById, notNullValue());
    }
}
