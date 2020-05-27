package com.android.kors;

import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.test.filters.MediumTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android.kors.activities.ActiveTaskListActivity;
import com.android.kors.helperClasses.CustomExpandableListAdapter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class ActiveChoresActyImplementedTest {
    @Rule
    public ActivityTestRule<ActiveTaskListActivity> rule = new ActivityTestRule<>(ActiveTaskListActivity.class);

    @Test
    public void confirmFilterOptionTextLabelIsPresent() throws Exception {
        ActiveTaskListActivity activity = rule.getActivity();
        View filterOptLabelById = activity.findViewById(R.id.active_list_instr);

        assertThat(filterOptLabelById, notNullValue());
    }

    @Test
    public void confirmFromDateTextLabelIsPresent() throws Exception {
        ActiveTaskListActivity activity = rule.getActivity();
        View fromDateLabelById = activity.findViewById(R.id.fromDateText);

        assertThat(fromDateLabelById, notNullValue());
    }

    @Test
    public void confirmFromDateInputBoxIsPresent() throws Exception {
        ActiveTaskListActivity activity = rule.getActivity();
        View fromDateInputBoxById = activity.findViewById(R.id.fromDateInput);

        assertThat(fromDateInputBoxById, notNullValue());
    }

    @Test
    public void confirmToDateTextLabelIsPresent() throws Exception {
        ActiveTaskListActivity activity = rule.getActivity();
        View toDateLabelById = activity.findViewById(R.id.toDateText);

        assertThat(toDateLabelById, notNullValue());
    }

    @Test
    public void confirmToDateInputBoxIsPresent() throws Exception {
        ActiveTaskListActivity activity = rule.getActivity();
        View toDateInputBoxById = activity.findViewById(R.id.toDateInput);

        assertThat(toDateInputBoxById, notNullValue());
    }

    @Test
    public void confirmSortTextLabelIsPresent() throws Exception {
        ActiveTaskListActivity activity = rule.getActivity();
        View sortTextLabelById = activity.findViewById(R.id.sortByText);

        assertThat(sortTextLabelById, notNullValue());
    }

    @Test
    public void confirmSortSpinnerIsPresent() throws Exception {
        ActiveTaskListActivity activity = rule.getActivity();
        View sortSpinnerById = activity.findViewById(R.id.sortOptionSpinner);

        assertThat(sortSpinnerById, notNullValue());
    }

    @Test
    public void confirmSortButtonIsPresent() throws Exception {
        ActiveTaskListActivity activity = rule.getActivity();
        View sortBtnById = activity.findViewById(R.id.sortButton);

        assertThat(sortBtnById, notNullValue());
    }

    @Test
    public void confirmActiveChoresListViewIsPresent() throws Exception {
        ActiveTaskListActivity activity = rule.getActivity();
        View activeChoresListViewById = activity.findViewById(R.id.expandableLV);
        ExpandableListView listView = (ExpandableListView) activeChoresListViewById;
        ExpandableListAdapter adapter = listView.getExpandableListAdapter();

        assertThat(activeChoresListViewById, notNullValue());
        assertThat(adapter, instanceOf(CustomExpandableListAdapter.class));
        assertThat(adapter.getGroupCount(), lessThan(100));     // possibility of not used
    }
}
