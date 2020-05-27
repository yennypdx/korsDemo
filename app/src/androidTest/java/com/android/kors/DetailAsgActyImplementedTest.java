package com.android.kors;

import android.view.View;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android.kors.activities.DetailTaskActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DetailAsgActyImplementedTest {
    @Rule
    public ActivityTestRule<DetailTaskActivity> rule =
            new ActivityTestRule<>(DetailTaskActivity.class);

    // Check points:
    // making sure all items are present at run time
    @Test
    public void confirmStatusImgViewIsPresent() throws Exception {
        DetailTaskActivity activity = rule.getActivity();
        View statusImgById = activity.findViewById(R.id.statusImg);

        assertThat(statusImgById, notNullValue());
    }

    @Test
    public void confirmStatusLabelIsPresent() throws Exception {
        DetailTaskActivity activity = rule.getActivity();
        View statusLabelById = activity.findViewById(R.id.statusText);

        assertThat(statusLabelById, notNullValue());
    }

    @Test
    public void confirmStatusResultIsPresent() throws Exception {
        DetailTaskActivity activity = rule.getActivity();
        View statusResultById = activity.findViewById(R.id.statusResult);

        assertThat(statusResultById, notNullValue());
    }

    @Test
    public void confirmRoomNameLabelIsPresent() throws Exception {
        DetailTaskActivity activity = rule.getActivity();
        View roomLabelById = activity.findViewById(R.id.roomNameLabel);

        assertThat(roomLabelById, notNullValue());
    }

    @Test
    public void confirmRoomNameImgViewIsPresent() throws Exception {
        DetailTaskActivity activity = rule.getActivity();
        View roomNameImgById = activity.findViewById(R.id.roomImg);

        assertThat(roomNameImgById, notNullValue());
    }

    @Test
    public void confirmRoomNameResultIsPresent() throws Exception {
        DetailTaskActivity activity = rule.getActivity();
        View roomNameResultById = activity.findViewById(R.id.roomNameFinalOutput);

        assertThat(roomNameResultById, notNullValue());
    }

    @Test
    public void confirmTaskTypeLabelIsPresent() throws Exception {
        DetailTaskActivity activity = rule.getActivity();
        View taskTypeLabelById = activity.findViewById(R.id.taskTypeLabel);

        assertThat(taskTypeLabelById, notNullValue());
    }

    @Test
    public void confirmTaskImgViewIsPresent() throws Exception {
        DetailTaskActivity activity = rule.getActivity();
        View taskTypeImgById = activity.findViewById(R.id.taskImg);

        assertThat(taskTypeImgById, notNullValue());
    }

    @Test
    public void confirmTaskNameResultIsPresent() throws Exception {
        DetailTaskActivity activity = rule.getActivity();
        View taskNameResultById = activity.findViewById(R.id.taskNameFinalOutput);

        assertThat(taskNameResultById, notNullValue());
    }

    @Test
    public void confirmDateTimeLabelIsPresent() throws Exception {
        DetailTaskActivity activity = rule.getActivity();
        View dateTimeLabelById = activity.findViewById(R.id.dateTimeOutput);

        assertThat(dateTimeLabelById, notNullValue());
    }

    @Test
    public void confirmCompletionDateLabelIsPresent() throws Exception {
        DetailTaskActivity activity = rule.getActivity();
        View completionDateLabelById = activity.findViewById(R.id.dateCompletionTextView);

        assertThat(completionDateLabelById, notNullValue());
    }

    @Test
    public void confirmDateTimeImgViewIsPresent() throws Exception {
        DetailTaskActivity activity = rule.getActivity();
        View dateTimeImgById = activity.findViewById(R.id.dateImg);

        assertThat(dateTimeImgById, notNullValue());
    }

    @Test
    public void confirmDateResultIsPresent() throws Exception {
        DetailTaskActivity activity = rule.getActivity();
        View dateResultById = activity.findViewById(R.id.completionDateFinalOut);

        assertThat(dateResultById, notNullValue());
    }

    @Test
    public void confirmTimeResultIsPresent() throws Exception {
        DetailTaskActivity activity = rule.getActivity();
        View timeResultById = activity.findViewById(R.id.completionTimeFinalOut);

        assertThat(timeResultById, notNullValue());
    }

    @Test
    public void confirmRepeatImgViewIsPresent() throws Exception {
        DetailTaskActivity activity = rule.getActivity();
        View repeatImgById = activity.findViewById(R.id.repeatImg);

        assertThat(repeatImgById, notNullValue());
    }

    @Test
    public void confirmRepeatTempLabelIsPresent() throws Exception {
        DetailTaskActivity activity = rule.getActivity();
        View repeatTempLabelById = activity.findViewById(R.id.repeat_text);

        assertThat(repeatTempLabelById, notNullValue());
    }

    @Test
    public void confirmAssigneeLabelIsPresent() throws Exception {
        DetailTaskActivity activity = rule.getActivity();
        View assigneeLabelById = activity.findViewById(R.id.assigneeOutput);

        assertThat(assigneeLabelById, notNullValue());
    }

    @Test
    public void confirmAssigneeImgViewIsPresent() throws Exception {
        DetailTaskActivity activity = rule.getActivity();
        View assigneeImgById = activity.findViewById(R.id.assigneeImg);

        assertThat(assigneeImgById, notNullValue());
    }

    @Test
    public void confirmAssigneeResultIsPresent() throws Exception {
        DetailTaskActivity activity = rule.getActivity();
        View assigneeResultById = activity.findViewById(R.id.assigneeFinalOutput);

        assertThat(assigneeResultById, notNullValue());
    }

    @Test
    public void confirmConfirmTaskButtonIsPresent() throws Exception {
        DetailTaskActivity activity = rule.getActivity();
        View confirmBtnById = activity.findViewById(R.id.confirmButton);

        assertThat(confirmBtnById, notNullValue());
    }

    @Test
    public void confirmNegotiateTaskButtonIsPresent() throws Exception {
        DetailTaskActivity activity = rule.getActivity();
        View negotiateBtnById = activity.findViewById(R.id.negotiateButton);

        assertThat(negotiateBtnById, notNullValue());
    }

    @Test
    public void confirmCompleteTaskButtonIsPresent() throws Exception {
        DetailTaskActivity activity = rule.getActivity();
        View completeBtnById = activity.findViewById(R.id.completedButton);

        assertThat(completeBtnById, notNullValue());
    }
}
