package com.android.kors;


import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.test.filters.MediumTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android.kors.activities.CalendarActivity;
import com.android.kors.helperClasses.TaskRowAdapter;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class CalendarActyImplementedTest {
    @Rule
    public ActivityTestRule<CalendarActivity> rule = new ActivityTestRule<>(CalendarActivity.class);

    @Test
    public void confirmToolbarIsPresent() throws Exception {
        CalendarActivity activity = rule.getActivity();
        View toolbarById = activity.findViewById(R.id.toolbar);

        assertThat(toolbarById, notNullValue());
    }

    @Test
    public void confirmHomeButtonIsPresent() throws Exception {
        CalendarActivity activity = rule.getActivity();
        View homeBtnById = activity.findViewById(R.id.toDashboard_fab);

        assertThat(homeBtnById, notNullValue());
    }

    @Test
    public void confirmAddButtonIsPresent() throws Exception {
        CalendarActivity activity = rule.getActivity();
        View addBtnById = activity.findViewById(R.id.create_new_fab);

        assertThat(addBtnById, notNullValue());
    }

    @Test
    public void confirmCalendarViewIsPresent() throws Exception {
        CalendarActivity activity = rule.getActivity();
        View calendarViewById = activity.findViewById(R.id.calendar);

        assertThat(calendarViewById, notNullValue());
    }

    @Test
    public void confirmTaskListViewIsPresent() throws Exception {
        CalendarActivity activity = rule.getActivity();
        View taskListViewById = activity.findViewById(R.id.dateBasedTaskListView);
        ListView listView = (ListView) taskListViewById;
        ListAdapter adapter = listView.getAdapter();

        assertThat(taskListViewById, notNullValue());
        assertThat(adapter, IsInstanceOf.<ListAdapter>instanceOf(TaskRowAdapter.class));
    }

}
