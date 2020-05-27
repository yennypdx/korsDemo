package com.android.kors;

import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android.kors.activities.ModifyMemberPermissionActivity;
import com.android.kors.helperClasses.PermissionRowAdapter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.IsInstanceOf.instanceOf;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@SmallTest
@RunWith(AndroidJUnit4.class)
public class ModifyMemberPermissionActyImplementedTest {
    @Rule
    public ActivityTestRule<ModifyMemberPermissionActivity> rule =
            new ActivityTestRule<>(ModifyMemberPermissionActivity.class);

    // Check points:
    // making sure all items are present at run time
    @Test
    public void confirmModifyMemberLabelIsPresent() throws Exception {
        ModifyMemberPermissionActivity activity = rule.getActivity();
        View modifyMemLabelById = activity.findViewById(R.id.modifyMemberLabelText);

        assertThat(modifyMemLabelById, notNullValue());
    }

    @Test
    public void confirmNameHeadingLabelIsPresent() throws Exception {
        ModifyMemberPermissionActivity activity = rule.getActivity();
        View nameHeadingLabelById = activity.findViewById(R.id.nameLabel_modMem);

        assertThat(nameHeadingLabelById, notNullValue());
    }

    @Test
    public void confirmAdminHeadingLabelIsPresent() throws Exception {
        ModifyMemberPermissionActivity activity = rule.getActivity();
        View adminHeadingLabelById = activity.findViewById(R.id.adminLabel_modMem);

        assertThat(adminHeadingLabelById, notNullValue());
    }

    @Test
    public void confirmMemberHeadingLabelIsPresent() throws Exception {
        ModifyMemberPermissionActivity activity = rule.getActivity();
        View memberHeadingLabelById = activity.findViewById(R.id.memberLabel_modMem);

        assertThat(memberHeadingLabelById, notNullValue());
    }

    @Test
    public void confirmModifyMemberListViewIsPresent() throws Exception {
        ModifyMemberPermissionActivity activity = rule.getActivity();
        View modificationListViewById = activity.findViewById(R.id.modifyMemberListView);
        ListView listView = (ListView) modificationListViewById;
        ListAdapter adapter = listView.getAdapter();

        assertThat(modificationListViewById, notNullValue());
        assertThat(adapter, instanceOf(PermissionRowAdapter.class));
    }

}
