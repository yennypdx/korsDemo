package com.android.kors;

import android.view.View;

import androidx.test.filters.SmallTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android.kors.activities.ScanGroupActivity;

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
public class ScanGroupActyImplementedTest {
    @Rule
    public ActivityTestRule<ScanGroupActivity> rule  = new ActivityTestRule<>(ScanGroupActivity.class);

    @Test
    public void confirmScanBtnIsPresent() throws Exception {
        ScanGroupActivity activity = rule.getActivity();
        View scanBtnById = activity.findViewById(R.id.scannerButton);

        assertThat(scanBtnById, notNullValue());
    }

    @Test
    public void confirmTextLabelBoxIsPresent() throws Exception {
        ScanGroupActivity activity = rule.getActivity();
        View textLabelById = activity.findViewById(R.id.code_con_text);

        assertThat(textLabelById, notNullValue());
    }

    @Test
    public void confirmResultTextLabelBoxIsPresent() throws Exception {
        ScanGroupActivity activity = rule.getActivity();
        View resultTextLabelById = activity.findViewById(R.id.codeConfirmation);

        assertThat(resultTextLabelById, notNullValue());
    }

    @Test
    public void confirmNextBtnIsPresent() throws Exception {
        ScanGroupActivity activity = rule.getActivity();
        View nextBtnById = activity.findViewById(R.id.nextToCollectMemberDataFromScan);

        assertThat(nextBtnById, notNullValue());
    }

}
