package com.android.kors;

import android.view.View;

import androidx.test.filters.SmallTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android.kors.activities.GetRoomListActivity;

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
public class GetRoomsActyImplementedTest {
    @Rule
    public ActivityTestRule<GetRoomListActivity> rule  = new ActivityTestRule<>(GetRoomListActivity.class);

    @Test
    public void confirmTaskImageBoxIsPresent() throws Exception {
        GetRoomListActivity activity = rule.getActivity();
        View roomImgBoxById = activity.findViewById(R.id.imgRoomBox);

        assertThat(roomImgBoxById, notNullValue());
    }

    @Test
    public void confirmGetGroupTextLabelIsPresent() throws Exception {
        GetRoomListActivity activity = rule.getActivity();
        View grTextLabelById = activity.findViewById(R.id.roomTextLabel);

        assertThat(grTextLabelById, notNullValue());
    }

    @Test
    public void confirmTableContainerIsPresent() throws Exception {
        GetRoomListActivity activity = rule.getActivity();
        View containerById = activity.findViewById(R.id.roomsEditTextContainer);

        assertThat(containerById, notNullValue());
    }

    @Test
    public void confirmAddRoomBtnIsPresent() throws Exception {
        GetRoomListActivity activity = rule.getActivity();
        View roomBtnById = activity.findViewById(R.id.addRoomButton);

        assertThat(roomBtnById, notNullValue());
    }

    @Test
    public void confirmDeleteRoomBtnIsPresent() throws Exception {
        GetRoomListActivity activity = rule.getActivity();
        View deleteBtnById = activity.findViewById(R.id.deleteRoomButton);

        assertThat(deleteBtnById, notNullValue());
    }

    @Test
    public void confirmNextToTaskBtnIsPresent() throws Exception {
        GetRoomListActivity activity = rule.getActivity();
        View nextBtnById = activity.findViewById(R.id.nextToGetTaskTypesButton);

        assertThat(nextBtnById, notNullValue());
    }
}
