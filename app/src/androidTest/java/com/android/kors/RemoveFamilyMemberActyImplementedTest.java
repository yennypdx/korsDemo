package com.android.kors;

import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android.kors.activities.RemoveMemberActivity;
import com.android.kors.helperClasses.ListViewItemCheckboxBaseAdapter;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@SmallTest
@RunWith(AndroidJUnit4.class)
public class RemoveFamilyMemberActyImplementedTest {
    @Rule
    public ActivityTestRule<RemoveMemberActivity> rule =
            new ActivityTestRule<>(RemoveMemberActivity.class);

    @Test
    public void confirmAdminListLabelIsPresent() throws Exception {
        RemoveMemberActivity activity = rule.getActivity();
        View adminListLabelById = activity.findViewById(R.id.adminListLabel);

        assertThat(adminListLabelById, notNullValue());
    }

    @Test
    public void confirmAdminListViewIsPresent() throws Exception {
        RemoveMemberActivity activity = rule.getActivity();
        View AdminListById = activity.findViewById(R.id.listViewAdmin);
        ListView listView = (ListView) AdminListById;
        ListAdapter adapter = listView.getAdapter();

        assertThat(AdminListById, notNullValue());
        assertThat(adapter, instanceOf(ListViewItemCheckboxBaseAdapter.class));
    }

    @Test
    public void confirmMemberListLabelIsPresent() throws Exception {
        RemoveMemberActivity activity = rule.getActivity();
        View memberListLabelById = activity.findViewById(R.id.memberListLabel);

        assertThat(memberListLabelById, notNullValue());
    }

    @Test
    public void confirmMemberListViewIsPresent() throws Exception {
        RemoveMemberActivity activity = rule.getActivity();
        View MemberListById = activity.findViewById(R.id.listViewMember);
        ListView listView = (ListView) MemberListById;
        ListAdapter adapter = listView.getAdapter();

        assertThat(MemberListById, notNullValue());
        assertThat(adapter, instanceOf(ListViewItemCheckboxBaseAdapter.class));
    }

    @Test
    public void confirmRemoveButtonIsPresent() throws Exception {
        RemoveMemberActivity activity = rule.getActivity();
        View removeBtnById = activity.findViewById(R.id.removeMemberButton);

        assertThat(removeBtnById, notNullValue());
    }
}
